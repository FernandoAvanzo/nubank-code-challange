package code.challenge.core.application.adapters.stdout

import code.challenge.core.domain.model.stockgain.Tax
import org.junit.Test

class OutputTaxsTests {

    @Test
    fun should_taxes_printed_how_strings() {
        val given = listOf(
            Tax(tax = 0.00),
            Tax(tax = 10000.0),
        )

        val whendo = given.encodeTaxs()

        val then = """[{"tax":0},{"tax":10000}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }
}