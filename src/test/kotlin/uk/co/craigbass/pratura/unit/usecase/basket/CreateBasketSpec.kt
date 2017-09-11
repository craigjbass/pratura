package uk.co.craigbass.pratura.unit.usecase.basket

import com.madetech.clean.usecase.execute
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.usecase.basket.CreateBasket
import uk.co.craigbass.pratura.usecase.basket.CreateBasket.BasketInitialiser

class CreateBasketSpec : Spek({
  it("can create a new basket") {
    CreateBasket(basketInitialiser("abc")).execute().basketId.shouldEqual("abc")
    CreateBasket(basketInitialiser("zea123")).execute().basketId.shouldEqual("zea123")
  }
})

private fun basketInitialiser(nextBasketId: String): BasketInitialiser {
  return object : BasketInitialiser {
    override fun new() = nextBasketId
  }
}
