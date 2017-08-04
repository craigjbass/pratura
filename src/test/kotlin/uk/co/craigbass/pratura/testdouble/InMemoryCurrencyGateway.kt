package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.domain.Currency
import uk.co.craigbass.pratura.gateway.CurrencyGateway

class InMemoryCurrencyGateway : CurrencyGateway {
  lateinit var currency: Currency

  override fun set(currency: Currency) {
   this.currency = currency
  }
  override fun getCurrentCurrency(): Currency {
    return currency
  }
}
