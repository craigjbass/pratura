package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.Currency

interface CurrencySetter {
  fun set(currency: Currency)
}
