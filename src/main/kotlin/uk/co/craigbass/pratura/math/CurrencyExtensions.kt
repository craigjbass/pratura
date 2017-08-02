package uk.co.craigbass.pratura.math

import java.math.BigDecimal

fun String.toDecimal(): BigDecimal = BigDecimal(this)

fun Int.toDecimal(): BigDecimal = BigDecimal(this)

fun BigDecimal.toCurrencyWithSymbol() = "£${toCurrencyString()}"

private fun BigDecimal.toCurrencyString() = this.setScale(2).toPlainString()
