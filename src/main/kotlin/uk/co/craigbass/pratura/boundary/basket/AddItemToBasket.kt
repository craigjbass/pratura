package uk.co.craigbass.pratura.boundary.basket

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket.Request

interface AddItemToBasket : SynchronousUseCase<Request, Unit> {
  data class Request(val quantity: Int, val sku: String)
}
