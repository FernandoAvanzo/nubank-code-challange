package code.challenge.core.domain.model.stockgain

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
        totalOperationValue(
            op.quantity,
            op.unitCost
        ) <= TAX_EXEMPTION -> Tax(0.00)
        else -> taxcalc(operations, op)
    }
}

//Todo A função não esta calculando corretamente,
fun taxcalc(operations: List<Operation>, op: Operation) = op
    .takeIf {
        it.operation == SELL && it.unitCost > weightedAveragePrice(operations)
    }?.run {
        Tax(
            (unitCost - loss(operations)) * TAX_RANGE
        )
    } ?: Tax()

fun loss(operations: List<Operation>) = operations
    .filter { it.operation == SELL }.sumOf {
        it.unitCost.takeIf { unitCost ->
            unitCost >= weightedAveragePrice(operations)
        }?.run { ZERO_LOSS } ?: it.unitCost
    }


fun weightedAveragePrice(operations: List<Operation>) = sumList(
    operations, VTO,
    isNotBuy = { sum -> sum + 0 },
    isBuy = { operation, sum ->
        sum + totalOperationValue(operation.quantity, operation.unitCost).toDouble()
    }).run {
    operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.let {
        sumList(it, QAN,
            isNotBuy = { sum -> sum + 0 },
            isBuy = { operation, sum -> sum + operation.quantity })
    }?.let {
        (this / it).format()
    } ?: this.format()
}


fun totalOperationValue(quantity: Int, unitcost: Number): Double = unitcost.run {
    toDouble() * quantity
}

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