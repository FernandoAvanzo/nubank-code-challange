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

        val whendo = weightedAveragePrice(given)
        val then = 16.67

        assert(whendo == then)
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

        val whendo = taxrule(given)
        val then = Tax(0.0)

        assert(whendo.first().tax == then.tax)
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

        val whendo = taxrule(given)
        val then = Tax(0.0)

        assert(whendo.first().tax == then.tax)
    }


    @Test
    fun should_calc_tax_in_each_operation_case01() {
        val given = listOf(
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

        val whendo = taxrule(given)

        val than = listOf(
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

        assert(than.size == whendo.size)
        assert(than
            .zip(whendo)
            .all {
                it.first.tax == it.second.tax
            }
        )
    }

    @Test
    fun should_calc_tax_in_each_operation_case02() {
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
            )
        )

        val whendo = taxrule(given)

        val than = listOf(
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 10000.0
            ),
            Tax(
                tax = 0.0
            )
        )

        assert(than.size == whendo.size)
        assert(than
            .zip(whendo)
            .all {
                it.first.tax == it.second.tax
            }
        )
    }
}