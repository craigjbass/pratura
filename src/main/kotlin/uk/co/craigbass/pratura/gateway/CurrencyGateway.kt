package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.CurrencyRetriever
import uk.co.craigbass.pratura.usecase.administration.CurrencySetter

interface CurrencyGateway : CurrencyRetriever, CurrencySetter
