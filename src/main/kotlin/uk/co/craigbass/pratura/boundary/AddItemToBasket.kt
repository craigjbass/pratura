package uk.co.craigbass.pratura.boundary

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.AddItemToBasket.Request

interface AddItemToBasket : SynchronousUseCase<Request, Unit> {
  data class Request(val quantity: Int, val sku: String)
}
