package uk.co.craigbass.pratura.unit.testdouble

import uk.co.craigbass.pratura.domain.Currency
import uk.co.craigbass.pratura.usecase.CurrencyRetriever

class StubCurrencyRetriever(private val currency: Currency) : CurrencyRetriever {
  override fun getCurrentCurrency() = currency
}
