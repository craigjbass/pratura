package uk.co.craigbass.pratura.unit

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEmpty
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever
import uk.co.craigbass.pratura.usecase.ViewBasket

class ViewBasketSpec : Spek({
    var viewBasket: ViewBasket? = null
    val basketContents = memoized { viewBasket!!.execute() }

    given("no items are added to the basket") {
        beforeEachTest {
            viewBasket = ViewBasket(object : BasketItemsRetriever {
                override fun all(): List<BasketItem> = listOf()
            })
        }

        it("should contain no items") {
            basketContents().items.shouldBeEmpty()
        }

        it("should have a total value of zero") {
            basketContents().basketValue.shouldBe("£0.00")
        }
    }

    given("one item is in the basket") {
        beforeEachTest {
            viewBasket = ViewBasket(object : BasketItemsRetriever {
                override fun all(): List<BasketItem> {
                    return listOf(BasketItem())
                }
            })
        }

        it("should contain no items") {
            basketContents().items.count().shouldBe(1)
        }
    }
})