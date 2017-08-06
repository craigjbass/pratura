package uk.co.craigbass.pratura.boundary.administration

import com.madetech.clean.usecase.SynchronousUseCase

interface SetStoreCurrency : SynchronousUseCase<SetStoreCurrency.Request, Unit> {
  data class Request(val currency: String, val country: String, val language: String)
}
