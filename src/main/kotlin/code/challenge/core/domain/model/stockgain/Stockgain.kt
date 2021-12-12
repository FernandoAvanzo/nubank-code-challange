package code.challenge.core.domain.model.stockgain

const val BUY = "buy"
const val SELL = "sell"

fun taxapply(operation: List<Operation>): List<Tax> = emptyList()

fun weightedAveragePrice(operations: List<Operation>) = sumOperations(
    operations, 0.00,
    { sum -> sum + 0 },
    { operation, sum ->
        sum + totalOperationValue(operation.quantity, operation.unitCost).toDouble()
    }).run {
    operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.let {
        sumOperations(it, 0,
            { sum -> sum + 0 },
            { operation, sum -> sum + operation.quantity })
    }?.let {
        (this / it).format()
    } ?: this.format()
}


fun totalOperationValue(quantity: Int, unitcost: Number): Number = unitcost.run {
    toDouble() * quantity
}

private fun Double.format() = String.format("%.2f", this).toDouble()

private fun <T : Number> sumOperations(
    operations: List<Operation>,
    sumStart: T,
    actionFalse: (T) -> T,
    actionTrue: (Operation, T) -> T
): T = operations
    .fold(sumStart) { sum, operation ->
        buyOperation(operation, sum, actionFalse, actionTrue)
    }

private fun <T : Number> buyOperation(
    operation: Operation,
    acc: T,
    actionFalse: (T) -> T,
    actionTrue: (Operation, T) -> T
): T = when (operation.operation) {
    BUY -> actionTrue(operation, acc)
    else -> actionFalse(acc)
}