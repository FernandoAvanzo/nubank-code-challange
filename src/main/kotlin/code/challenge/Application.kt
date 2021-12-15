package code.challenge

import code.challenge.core.application.adapters.stdin.decodeOperations
import code.challenge.core.application.adapters.stdout.encodeTaxs
import code.challenge.core.domain.model.stockgain.taxrule

fun main(args: Array<String>): Unit = args
    .takeIf { it.isNotEmpty() }?.forEach {
        println(calcStocksGainTaxs(it))
    } ?: println("Nenhuma Lista De Operações encontrada")

fun calcStocksGainTaxs(operations: String) = operations.run {
    taxrule(decodeOperations()).encodeTaxs()
}