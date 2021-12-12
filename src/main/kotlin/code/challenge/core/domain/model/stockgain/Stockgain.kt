package code.challenge.core.domain.model.stockgain

const val BUY = "buy"
const val SELL = "sell"
const val VTO = 0.00
const val QAN = 0
const val TAX_FREE = 20000.00

fun taxapply(operations: List<Operation>): List<Tax> = emptyList()

fun taxrule(operations: List<Operation>) = operations.sumOf { op ->
    when {
        op.operation == BUY -> 0.0
        totalOperationValue(
            op.quantity,
            op.unitCost
        ).toDouble() <= TAX_FREE -> 0.0
        else -> taxcalc(operations)
    }
}


fun taxcalc(operations: List<Operation>) = losscal(operations) * 0.2

//TODO Revisar esse metodo porque ele não esta passando
fun losscal(operations: List<Operation>) = 0.00

fun weightedAveragePrice(operations: List<Operation>) = sumList(
    operations, VTO,
    { sum -> sum + 0 },
    { operation, sum ->
        sum + totalOperationValue(operation.quantity, operation.unitCost).toDouble()
    }).run {
    operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.let {
        sumList(it, QAN,
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

private fun <T : Number> sumList(
    operations: List<Operation>,
    sumStart: T,
    actionFalse: (T) -> T,
    actionTrue: (Operation, T) -> T
): T = operations
    .fold(sumStart) { sum, operation ->
        buyActions(operation, sum, actionFalse, actionTrue)
    }

private fun <T : Number> buyActions(
    operation: Operation,
    acc: T,
    actionFalse: (T) -> T,
    actionTrue: (Operation, T) -> T
): T = when (operation.operation) {
    BUY -> actionTrue(operation, acc)
    else -> actionFalse(acc)
}