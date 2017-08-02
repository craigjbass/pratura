package uk.co.craigbass.pratura.math

import java.math.BigDecimal

fun String.toDecimal(): BigDecimal = BigDecimal(this)
fun Int.toDecimal(): BigDecimal = BigDecimal(this)
fun BigDecimal?.toCurrencyWithSymbol() = if (this == null) "£0.00" else "£${toCurrencyString()}"
fun BigDecimal.toCurrencyString() = this.setScale(2).toPlainString()
