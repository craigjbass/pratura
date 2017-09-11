package uk.co.craigbass.pratura.boundary.basket

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.basket.CreateBasket.Response

interface CreateBasket : SynchronousUseCase<Unit, Response> {
  data class Response(val basketId: String)
}
