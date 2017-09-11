package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.boundary.basket.CreateBasket
import uk.co.craigbass.pratura.boundary.basket.CreateBasket.Response

class CreateBasket(private val basketInitialiser: BasketInitialiser) : CreateBasket {
  override fun execute(request: Unit): Response {
    return Response(basketId = basketInitialiser.new())
  }

  interface BasketInitialiser {
    fun new(): String
  }
}
