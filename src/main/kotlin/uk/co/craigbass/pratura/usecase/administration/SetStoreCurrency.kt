package uk.co.craigbass.pratura.usecase.administration

import uk.co.craigbass.pratura.boundary.administration.SetStoreCurrency
import uk.co.craigbass.pratura.boundary.administration.SetStoreCurrency.Request
import uk.co.craigbass.pratura.domain.Currency

class SetStoreCurrency(private val currencySetter: CurrencySetter) : SetStoreCurrency {
  override fun execute(request: Request) {
    currencySetter.set(Currency(request.currency, request.country, request.language))
  }

}
