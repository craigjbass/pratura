package uk.co.craigbass.pratura.unit

import org.amshove.kluent.shouldBe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.AddItemToBasket.Request
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.testdouble.*
import uk.co.craigbass.pratura.usecase.AddItemToBasket

class AddItemToBasketSpec : Spek({
  var basketItems: List<BasketItem>? = null
  val basketItemsGateway = memoized { SpyBasketItemsSaver() }
  val addItemToBasket = memoized {
    AddItemToBasket(
      basketItemsGateway(),
      StubBasketItemsRetriever(basketItems!!)
    )
  }

  given("basket contains nothing and one item is added to basket") {
    beforeEachTest {
      basketItems = listOf()
      addItemToBasket().execute(Request(1, "sku"))
    }

    it("saves a new item to the basket") {
      basketItemsGateway().lastSavedItem().quantity.shouldBe(1)
      basketItemsGateway().lastSavedItem().sku.shouldBe("sku")
    }
  }

  given("one item is already in the basket") {
    beforeEachTest {
      basketItems = listOf(BasketItem(1, "sku"))
      addItemToBasket().execute(Request(1, "sku"))
    }

    it("should update basket item with new quantity") {
      basketItemsGateway().lastSavedItem().quantity.shouldBe(2)
    }
  }
})
