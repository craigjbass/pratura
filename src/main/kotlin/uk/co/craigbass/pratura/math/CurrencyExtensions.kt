package uk.co.craigbass.pratura.math

import java.math.BigDecimal

fun String.toDecimal(): BigDecimal = BigDecimal(this)
fun Int.toDecimal(): BigDecimal = BigDecimal(this)
