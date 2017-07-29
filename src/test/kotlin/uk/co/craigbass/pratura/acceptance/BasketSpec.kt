package uk.co.craigbass.pratura.acceptance

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEmpty
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.boundary.AddItemToBasket.AddItemToBasketRequest
import uk.co.craigbass.pratura.testdouble.InMemoryBasketItemsGateway
import uk.co.craigbass.pratura.usecase.AddItemToBasket
import uk.co.craigbass.pratura.usecase.ViewBasket

class BasketSpec : Spek({
    val basketItemsGateway = memoized { InMemoryBasketItemsGateway() }

    val viewBasket = ViewBasket(basketItemsGateway())

    val basketContents = memoized { viewBasket.execute() }

    given("no lineItems are added to the basket") {
        it("should contain no lineItems") {
            basketContents().lineItems.shouldBeEmpty()
        }

        it("should have a total value of zero") {
            basketContents().basketValue.shouldBe("£0.00")
        }
    }

    given("one item is in the basket") {
        val addItemToBasket = AddItemToBasket(basketItemsGateway())

        beforeEachTest {
            addItemToBasket.execute(AddItemToBasketRequest(1, "productsku"))
        }

        val lineItems = memoized { basketContents().lineItems }

        it("should contain one item") {
            lineItems().count().shouldBe(1)
        }

        it("should have the correct sku") {
            lineItems().first().sku.shouldBe("productsku")
        }

        it("should have the correct quantity") {
            lineItems().first().quantity.shouldBe(1)
        }
    }
})
