package code.challenge
import kotlin.test.Test

class ApplicationTest{

    @Test
    fun should_calc_stocks_gain_taxs(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 100},{"operation":"sell", "unit-cost":15, "quantity": 50},{"operation":"sell", "unit-cost":15, "quantity": 50}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":0}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }
}