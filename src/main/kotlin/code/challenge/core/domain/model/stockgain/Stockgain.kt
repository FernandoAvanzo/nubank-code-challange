package code.challenge.core.domain.model.stockgain

const val BUY = "buy"
const val SELL = "sell"

fun taxapply(operation: List<Operation>): List<Tax> = emptyList()

fun weightedAveragePrice(operations: List<Operation>) = operations.let { operations ->
    operations.fold(0.00) { sum, operation ->
        when (operation.operation) {
            BUY -> {
                sum + totalOperationValue(operation.quantity, operation.unitCost)
            }
            else -> sum + 0
        }
    }.let { stockPrice ->
        operations.filter { it.operation == BUY }.takeIf { it.isNotEmpty() }?.fold(0) { sum, operation ->
            when (operation.operation) {
                BUY -> {
                    sum + operation.quantity
                }
                else -> sum + 0
            }
        }?.let { unitsBuyed ->
            stockPrice / unitsBuyed
        }?.let {
            String.format("%.2f",it).toDouble()
        } ?: stockPrice.let{
            String.format("%.2f",it).toDouble()
        }

    }
}

fun totalOperationValue(quantity: Int, unitcost: Number) =  unitcost.run {
    toDouble() * quantity
}