package code.challenge.core.domain.model.stockgain

import kotlin.math.absoluteValue

const val BUY = "buy"
const val SELL = "sell"
const val VTO = 0.00
const val QAN = 0
const val ZERO_LOSS = 0.00
const val TAX_EXEMPTION = 20000.00
const val TAX_RANGE = 0.2

fun taxrule(operations: List<Operation>) = operations.map { op ->
    when {
        op.operation == BUY -> Tax(0.00)
        totalOperation(
            op.quantity,
            op.unitCost
        ) <= TAX_EXEMPTION -> Tax(0.00)
        else -> taxapply(operations, op)
    }
}

fun taxapply(operations: List<Operation>, op: Operation) = weightedAveragePrice(operations)
    .let { wap ->
        op.takeIf {
            it.operation == SELL && it.unitCost >= wap
        }?.run { taxcalc(operations, op, wap) } ?: Tax()
    }

fun taxcalc(operations: List<Operation>, op: Operation, weightedAveragePrice: Double) = Tax(
    tax = descountLoss(
        profit(
            operation = op,
            weightedAveragePrice
        ),
        loss(operations.previousOperations(op),weightedAveragePrice)
    ) * TAX_RANGE
)

fun descountLoss(profit: Double, loss: Double) = profit - loss

fun profit(operation: Operation, weightedAveragePrice: Double) = operation.run {
    totalOperation(quantity, unitCost) - totalOperation(quantity, weightedAveragePrice)
}

fun loss(operations: List<Operation>, weightedAveragePrice: Double) = operations
    .filter { it.operation == SELL }.sumOf {
        it.unitCost.takeIf { unitCost ->
            unitCost >= weightedAveragePrice
        }?.run { ZERO_LOSS } ?: profit(it, weightedAveragePrice)
    }.absoluteValue

fun weightedAveragePrice(operations: List<Operation>) = sumList(
    operations, VTO,
    isNotBuy = { sum -> sum + 0 },
    isBuy = { operation, sum ->
        sum + totalOperation(operation.quantity, operation.unitCost)
    }).run {
    operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.let {
        sumList(it, QAN,
            isNotBuy = { sum -> sum + 0 },
            isBuy = { operation, sum -> sum + operation.quantity })
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