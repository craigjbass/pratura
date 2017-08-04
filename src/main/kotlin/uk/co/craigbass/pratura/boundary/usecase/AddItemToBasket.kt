package uk.co.craigbass.pratura.boundary.usecase

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.usecase.AddItemToBasket.Request

interface AddItemToBasket : SynchronousUseCase<Request, Unit> {
  data class Request(val quantity: Int, val sku: String)
}
