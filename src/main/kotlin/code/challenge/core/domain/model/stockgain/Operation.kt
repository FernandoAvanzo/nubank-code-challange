package code.challenge.core.domain.model.stockgain

data class Operation(
    val operation: String = "",
    val quantity: Int = 0,
    val unitCost: Double = 0.0,
)
