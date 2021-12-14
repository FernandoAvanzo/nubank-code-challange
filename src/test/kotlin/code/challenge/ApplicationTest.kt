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

    @Test
    fun should_calc_stocks_gain_taxs_case01(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 100},{"operation":"sell", "unit-cost":15, "quantity": 50},{"operation":"sell", "unit-cost":15, "quantity": 50}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":0}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }

    @Test
    fun should_calc_stocks_gain_taxs_case02(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell",
"unit-cost":20, "quantity": 5000},{"operation":"sell", "unit-cost":5, "quantity":
5000}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":10000},{"tax":0}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }

    @Test
    fun should_calc_stocks_gain_taxs_case03(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell",
"unit-cost":5, "quantity": 5000},{"operation":"sell", "unit-cost":20, "quantity":5000}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":5000}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }

    @Test
    fun should_calc_stocks_gain_taxs_case04(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"buy",
"unit-cost":25, "quantity": 5000},{"operation":"sell", "unit-cost":15,
"quantity": 10000}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":0}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }

    @Test
    fun should_calc_stocks_gain_taxs_case05(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"buy",
"unit-cost":25, "quantity": 5000},{"operation":"sell", "unit-cost":15,
"quantity": 10000},{"operation":"sell", "unit-cost":25, "quantity": 5000}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":0},{"tax":10000}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }

    @Test
    fun should_calc_stocks_gain_taxs_case06(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell",
"unit-cost":2, "quantity": 5000},{"operation":"sell", "unit-cost":20, "quantity":
2000},{"operation":"sell", "unit-cost":20, "quantity": 2000},{"operation":"sell",
"unit-cost":25, "quantity": 1000}]"""

        val whendo = calcStocksGainTaxs(given)

        val then = """[{"tax":0},{"tax":0},{"tax":0},{"tax":0},{"tax":3000}]"""

        assert(whendo == then)
        { "${whendo}==${then}" }
    }
}