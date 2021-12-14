package code.challenge.core.application.adapters.stdout

import code.challenge.core.domain.model.stockgain.Tax
import kotlinx.serialization.json.*
import kotlinx.serialization.*


fun List<Tax>.encodeTaxs(): String = this
    .map {
        ShadowTax(it.tax.toInt())
    }.let { Json.encodeToString(it) }

@Serializable
data class ShadowTax(
    @SerialName("tax")
    val tax: Int,
)