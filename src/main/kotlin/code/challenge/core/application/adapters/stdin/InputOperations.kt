package code.challenge.core.application.adapters.stdin

import code.challenge.core.domain.model.stockgain.Operation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val json = Json { isLenient = true }

fun String.decodeOperations(): List<Operation> = json
    .decodeFromString<List<OperationIn>>(this)
    .map {
        Operation(
            it.operation,
            it.quantity,
            it.unitCost
        )
    }

@Serializable
data class OperationIn(
    @SerialName("operation")
    val operation: String = "",
    @SerialName("quantity")
    val quantity: Int = 0,
    @SerialName("unit-cost")
    val unitCost: Double = 0.0
)
