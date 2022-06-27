import code.challenge.core.application.adapters.stdin.decodeOperations
import code.challenge.core.application.adapters.stdout.encodeTaxes
import code.challenge.core.domain.model.stockgain.taxApply

fun main(args: Array<String>): Unit = args
    .takeIf { it.isNotEmpty() }?.forEach {
        println(calcStocksGainTaxs(it))
    } ?: println("Nenhuma Lista De Operações encontrada")

fun calcStocksGainTaxs(operations: String) = operations.run {
    taxApply(decodeOperations()).encodeTaxes()
}
