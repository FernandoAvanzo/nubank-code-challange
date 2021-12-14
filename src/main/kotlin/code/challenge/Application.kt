package code.challenge

import code.challenge.core.application.adapters.stdin.decodeOperations
import code.challenge.core.application.adapters.stdout.encodeTaxs
import code.challenge.core.domain.model.stockgain.taxrule

fun main() {
    print("hellow world!!")
}

fun calcStocksGainTaxs(operations: String) = operations.run {
    taxrule(decodeOperations()).encodeTaxs()
}