package code.challenge.core.domain.model.stockgain

import code.challenge.core.domain.model.stockgain.StockgainConstants.BUY
import code.challenge.core.domain.model.stockgain.StockgainConstants.QAN
import code.challenge.core.domain.model.stockgain.StockgainConstants.SELL
import code.challenge.core.domain.model.stockgain.StockgainConstants.TAX_EXEMPTION
import code.challenge.core.domain.model.stockgain.StockgainConstants.TAX_INDEX
import code.challenge.core.domain.model.stockgain.StockgainConstants.VTO
import code.challenge.core.domain.model.stockgain.StockgainConstants.ZERO_LOSS
import kotlin.math.absoluteValue

fun taxApply(operations: List<Operation>) = weightedAveragePrice(operations.filterByType(BUY))
    .let { wap ->
        operations.map { op ->
            when {
                op.operation == BUY -> freeTax()
                isTaxFree(op) -> freeTax()
                else -> taxRule(operations.filterByType(SELL), op, wap)
            }
        }
    }

fun freeTax() = Tax(0.0)

fun isTaxFree(op: Operation) = totalOperation(op.quantity, op.unitCost) <= TAX_EXEMPTION

fun taxRule(operations: List<Operation>, op: Operation, weightedAveragePrice: Double) = op
    .takeIf {
        it.unitCost >= weightedAveragePrice
    }?.run { taxCalc(operations.previousOperations(op), op, weightedAveragePrice) } ?: freeTax()

fun taxCalc(operations: List<Operation>, op: Operation, weightedAveragePrice: Double) = Tax(
    tax = liquidProfit(
        profit(operation = op, weightedAveragePrice),
        loss(operations, weightedAveragePrice)
    ) * TAX_INDEX
)

fun liquidProfit(profit: Double, loss: Double) = (profit - loss).run {
    takeIf { it < 0 }?.let { ZERO_LOSS } ?: this
}

fun profit(operation: Operation, weightedAveragePrice: Double) = operation.run {
    totalOperation(quantity, unitCost) - totalOperation(quantity, weightedAveragePrice)
}

fun loss(operations: List<Operation>, weightedAveragePrice: Double) = operations
    .fold(0.0) { sum, op ->
        op.unitCost.takeIf { unitCost ->
            unitCost >= weightedAveragePrice
        }?.run { ZERO_LOSS } ?: (sum + profit(op, weightedAveragePrice))
    }.absoluteValue

fun weightedAveragePrice(buyOperations: List<Operation>) = sumList(
    buyOperations, VTO
) { operation, sum ->
    sum + totalOperation(operation.quantity, operation.unitCost)
}.run {
    buyOperations.takeIfNotEmpty()?.let {
        sumList(
            it, QAN
        ) { operation, sum -> sum + operation.quantity }
    }?.let {
        (this / it).format()
    } ?: this.format()
}

fun totalOperation(quantity: Int, unitcost: Double): Double = unitcost * quantity

fun List<Operation>.filterByType(type: String) = this.filter { it.operation == type }

fun List<Operation>.takeIfNotEmpty() = takeIf { it.isNotEmpty() }

private fun List<Operation>.previousOperations(op: Operation) = this.subList(0, this.indexOf(op))

private fun Double.format() = String.format("%.2f", this).toDouble()

private fun <T : Number> sumList(
    operations: List<Operation>,
    initialValue: T,
    sumOperation: (Operation, T) -> T
): T = operations
    .fold(initialValue) { sum, operation ->
        sumOperation(operation, sum)
    }
