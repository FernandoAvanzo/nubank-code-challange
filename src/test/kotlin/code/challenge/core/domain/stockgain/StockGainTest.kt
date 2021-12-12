package code.challenge.core.domain.stockgain

import code.challenge.core.domain.model.stockgain.*
import kotlin.test.Test


class StockGainTest {


    @Test
    fun should_weighted_Average_price_equals_16_66() {
        val given = listOf(
            Operation(
                operation = BUY,
                quantity = 10,
                unitCost = 20.00,
            ),
            Operation(
                operation = BUY,
                quantity = 5,
                unitCost = 10.00,
            )
        )

        val than = weightedAveragePrice(given)
        val whenResult = 16.67

        assert(than == whenResult)
    }

    @Test
    fun should_tax_free_for_stock_buyed() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.00,
                quantity = 100
            )
        )

        val then = taxrule(given)
        val whenResult = 0.0

        assert(then == whenResult)
    }

    @Test
    fun should_tax_free_for_profit_less_or_equals_than_20000() {
        val given = listOf(
            Operation(
                operation = SELL,
                unitCost = 15.00,
                quantity = 50
            )
        )

        val then = taxrule(given)
        val whenResult = 0.0

        assert(then == whenResult)
    }

    //Todo naõ entendi bem a regra do lucro e prejuizo. Preciso analisar ela novamente
    @Test
    fun should_tax_charged_for_profit_more_than_20000() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.00,
                quantity = 10000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.00,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 5.00,
                quantity = 5000
            ),
        )

        val then = taxrule(given)
        val whenResult = 10000.00

        assert(then == whenResult)
    }

    @Test
    fun should_calc_tax_in_each_operation_case01() {
        val givenInput = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.00,
                quantity = 100
            ),
            Operation(
                operation = SELL,
                unitCost = 15.00,
                quantity = 50
            ),
            Operation(
                operation = SELL,
                unitCost = 15.00,
                quantity = 50
            )
        )

        val whenResult = taxapply(givenInput)

        val thanOutput = listOf(
            Tax(
                tax = 0.00
            ),
            Tax(
                tax = 0.00
            ),
            Tax(
                tax = 0.00
            )
        )

        assert(thanOutput.size == whenResult.size)
        assert(thanOutput
            .zip(whenResult)
            .all {
                it.first.tax == it.second.tax
            }
        )
    }
}