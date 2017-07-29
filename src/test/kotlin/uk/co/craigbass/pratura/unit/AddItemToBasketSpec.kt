package uk.co.craigbass.pratura.unit

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBeNull
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.boundary.AddItemToBasket.AddItemToBasketRequest
import uk.co.craigbass.pratura.testdouble.SpyBasketItemsSaver
import uk.co.craigbass.pratura.usecase.AddItemToBasket

class AddItemToBasketSpec : Spek({
    val basketItemsGateway = memoized { SpyBasketItemsSaver() }
    val addItemToBasket = AddItemToBasket(basketItemsGateway())

    given("one item is added to basket") {
        beforeEachTest {
            addItemToBasket.execute(AddItemToBasketRequest(1, "sku"))
        }

        it("saves a new item to the basket") {
            basketItemsGateway().lastSavedItem().quantity.shouldBe(1)
            basketItemsGateway().lastSavedItem().sku.shouldBe("sku")
        }
    }
})
