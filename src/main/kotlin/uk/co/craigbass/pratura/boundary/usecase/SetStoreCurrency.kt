package uk.co.craigbass.pratura.boundary.usecase

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.usecase.SetStoreCurrency.Request

interface SetStoreCurrency : SynchronousUseCase<Request, Unit> {
  data class Request(val currency: String, val country: String, val language: String)
}
