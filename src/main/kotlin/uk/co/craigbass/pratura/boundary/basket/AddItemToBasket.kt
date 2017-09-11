package uk.co.craigbass.pratura.boundary.basket

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket.*

interface AddItemToBasket : SynchronousUseCase<Request, Response> {
  data class Request(val quantity: Int, val sku: String, val basketId: String)
  data class Response(val errors: Set<String>)
}
