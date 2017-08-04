package uk.co.craigbass.pratura.domain

import org.joda.money.*
import org.joda.money.format.MoneyFormatterBuilder
import java.math.BigDecimal
import java.util.*

class Currency(val currency: String, val country: String, val language: String) {
  fun format(amount: BigDecimal): String {
    val currencyUnit = CurrencyUnit.of(currency)
    val formatter = MoneyFormatterBuilder()
      .appendCurrencySymbolLocalized()
      .appendAmountLocalized()
      .toFormatter(Locale(language, country))
    return formatter.print(Money.of(currencyUnit, amount))
  }
}
