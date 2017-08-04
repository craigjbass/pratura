package uk.co.craigbass.pratura.domain

import org.joda.money.*
import org.joda.money.format.MoneyFormatterBuilder
import java.math.BigDecimal
import java.util.*

class Currency(val currency: String, val locale: Locale) {
  constructor(currency: String, country: String, language: String)
    : this(currency, Locale(language, country))

  val country: String get() = locale.country
  val language: String get() = locale.language

  private val moneyFormatter
    get() = MoneyFormatterBuilder()
      .appendCurrencySymbolLocalized()
      .appendAmountLocalized()
      .toFormatter(locale)

  fun format(amount: BigDecimal): String {
    return formatMoney(amount.toMoney())
  }

  private fun formatMoney(money: Money): String {
    return moneyFormatter.print(money)
  }

  private fun BigDecimal.toMoney() = Money.of(CurrencyUnit.of(currency), this)
}
