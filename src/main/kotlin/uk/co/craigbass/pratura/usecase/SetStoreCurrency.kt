package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.usecase.SetStoreCurrency
import uk.co.craigbass.pratura.boundary.usecase.SetStoreCurrency.Request
import uk.co.craigbass.pratura.domain.Currency

class SetStoreCurrency(val currencySetter: CurrencySetter) : SetStoreCurrency {
  override fun execute(request: Request) {
    currencySetter.set(Currency(request.currency, request.country, request.language))
  }

}
