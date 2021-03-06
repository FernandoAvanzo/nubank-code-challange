package code.challenge.core.application.adapters.stdin

import code.challenge.core.domain.model.stockgain.StockgainConstants.BUY
import code.challenge.core.domain.model.stockgain.Operation
import code.challenge.core.domain.model.stockgain.StockgainConstants.SELL
import kotlin.test.Test

class InputOperationsTests {

    @Test
    fun should_build_a_operations_list_from_string(){
        val given = """[{"operation":"buy", "unit-cost":10, "quantity": 10000}, {"operation":"sell","unit-cost":20, "quantity": 5000}]"""

        val `when` = given.decodeOperations()

        val then = listOf(
            Operation(
                operation = BUY,
                unitCost = 10.0,
                quantity = 10000
            ),
            Operation(
                operation = SELL,
                unitCost = 20.0,
                quantity = 5000
            )
        )

        assert(then.size == `when`.size)
        assert(then
            .zip(`when`)
            .all { it.first == it.second }
        ){ "${`when`}==${then}" }
    }
}
