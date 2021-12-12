package code.challenge.core.domain.stockgain

import code.challenge.core.domain.model.stockgain.BUY
import code.challenge.core.domain.model.stockgain.SELL
import code.challenge.core.domain.model.stockgain.taxapply
import code.challenge.core.domain.model.stockgain.Operation
import code.challenge.core.domain.model.stockgain.Tax


class StockGainTest {

    fun should_calc_tax_in_each_operation_case01(){
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

        assert(thanOutput.zip(whenResult).all{
            it.first.tax == it.second.tax
        })

    }
}