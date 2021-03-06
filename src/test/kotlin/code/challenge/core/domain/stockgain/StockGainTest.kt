package code.challenge.core.domain.stockgain

import code.challenge.core.domain.model.stockgain.Operation
import code.challenge.core.domain.model.stockgain.StockgainConstants.BUY
import code.challenge.core.domain.model.stockgain.StockgainConstants.SELL
import code.challenge.core.domain.model.stockgain.Tax
import code.challenge.core.domain.model.stockgain.filterByType
import code.challenge.core.domain.model.stockgain.loss
import code.challenge.core.domain.model.stockgain.taxApply
import code.challenge.core.domain.model.stockgain.weightedAveragePrice
import kotlin.test.Test

class StockGainTest {

    @Test
    fun should_weighted_Average_price_equals_16_67() {
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

        assert(whendo == then) { "$whendo==$then" }
    }

    @Test
    fun should_weighted_Average_price_equals_10() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.00,
                quantity = 10000
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

        val whendo = weightedAveragePrice(given.filterByType(BUY))
        val then = 10.00

        assert(whendo == then) { "$whendo==$then" }
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

        val whendo = taxApply(given)
        val then = Tax(0.0)

        assert(whendo.first().tax == then.tax) { "${whendo.first().tax}==${then.tax}" }
    }

    @Test
    fun should_not_have_loss() {
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
        )

        val whendo = loss(given, weightedAveragePrice(given))

        val then = 0.0

        assert(whendo == then) { "$whendo==$then" }
    }

    @Test
    fun should_have_loss() {
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

        val whendo = loss(given, weightedAveragePrice(given.filterByType(BUY)))

        val then = 25000.0

        assert(whendo == then) { "$whendo==$then" }
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

        val whendo = taxApply(given)
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

        val whendo = taxApply(given)

        val then = listOf(
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

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }

    @Test
    fun should_calc_tax_in_each_operation_case02() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 5.0,
                quantity = 5000
            )
        )

        val whendo = taxApply(given)

        val then = listOf(
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

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }

    @Test
    fun should_calc_tax_in_each_operation_case03() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = SELL,
                unitCost = 5.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.0,
                quantity = 5000
            )
        )

        val whendo = taxApply(given)

        val then = listOf(
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 5000.0
            )
        )

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }

    @Test
    fun should_calc_tax_in_each_operation_case04() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = BUY,
                unitCost = 25.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 15.0,
                quantity = 5000
            )
        )

        val whendo = taxApply(given)

        val then = listOf(
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            )
        )

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }

    @Test
    fun should_calc_tax_in_each_operation_case05() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = BUY,
                unitCost = 25.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 15.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 25.0,
                quantity = 5000
            )
        )

        val whendo = taxApply(given)

        val then = listOf(
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 10000.0
            )
        )

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }

    @Test
    fun should_calc_tax_in_each_operation_case06() {
        val given = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = SELL,
                unitCost = 2.0,
                quantity = 5000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.0,
                quantity = 2000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.0,
                quantity = 2000
            ),
            Operation(
                operation = SELL,
                unitCost = 25.0,
                quantity = 1000
            )
        )

        val whendo = taxApply(given)

        val then = listOf(
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 0.0
            ),
            Tax(
                tax = 3000.0
            )
        )

        assert(then.size == whendo.size)
        assert(
            then
                .zip(whendo)
                .all { it.first.tax == it.second.tax }
        ) { "$whendo==$then" }
    }
}
