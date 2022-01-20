package code.challenge.core.domain.model.stockgain

import code.challenge.core.domain.model.stockgain.StockgainConstants.BUY
import code.challenge.core.domain.model.stockgain.StockgainConstants.QAN
import code.challenge.core.domain.model.stockgain.StockgainConstants.SELL
import code.challenge.core.domain.model.stockgain.StockgainConstants.TAX_EXEMPTION
import code.challenge.core.domain.model.stockgain.StockgainConstants.TAX_RANGE
import code.challenge.core.domain.model.stockgain.StockgainConstants.VTO
import code.challenge.core.domain.model.stockgain.StockgainConstants.ZERO_LOSS
import kotlin.math.absoluteValue

fun taxrule(operations: List<Operation>) = weightedAveragePrice(operations)
    .let { wap ->
        operations.map { op ->
            when {
                op.operation == BUY -> Tax(0.00)
                totalOperation(
                    op.quantity,
                    op.unitCost
                ) <= TAX_EXEMPTION -> Tax(0.00)
                else -> taxapply(operations, op, wap)
            }
        }
    }

fun taxapply(operations: List<Operation>, op: Operation, weightedAveragePrice: Double) = op
    .takeIf {
        it.operation == SELL && it.unitCost >= weightedAveragePrice
    }?.run { taxcalc(operations.previousOperations(op), op, weightedAveragePrice) } ?: Tax()

fun taxcalc(operations: List<Operation>, op: Operation, weightedAveragePrice: Double) = Tax(
    tax = descountLoss(
        profit(
            operation = op,
            weightedAveragePrice
        ),
        loss(operations, weightedAveragePrice)
    ) * TAX_RANGE
)

fun descountLoss(profit: Double, loss: Double) = (profit - loss).run {
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

fun weightedAveragePrice(operations: List<Operation>) = sumList(
    operations, VTO,
    isNotBuy = { sum -> sum + 0 },
    isBuy = { operation, sum ->
        sum + totalOperation(operation.quantity, operation.unitCost)
    }
).run {
    operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.let {
        sumList(
            it, QAN,
            isNotBuy = { sum -> sum + 0 },
            isBuy = { operation, sum -> sum + operation.quantity }
        )
    }?.let {
        (this / it).format()
    } ?: this.format()
}

fun totalOperation(quantity: Int, unitcost: Double): Double = unitcost.run {
    this * quantity
}

private fun List<Operation>.previousOperations(op: Operation) = this.subList(0, this.indexOf(op))

private fun Double.format() = String.format("%.2f", this).toDouble()

private fun <T : Number> sumList(
    operations: List<Operation>,
    sumStart: T,
    isNotBuy: (T) -> T,
    isBuy: (Operation, T) -> T
): T = operations
    .fold(sumStart) { sum, operation ->
        buyActions(operation, sum, isNotBuy, isBuy)
    }

private fun <T : Number> buyActions(
    operation: Operation,
    acc: T,
    isNotBuy: (T) -> T,
    isBuy: (Operation, T) -> T
): T = when (operation.operation) {
    BUY -> isBuy(operation, acc)
    else -> isNotBuy(acc)
}
