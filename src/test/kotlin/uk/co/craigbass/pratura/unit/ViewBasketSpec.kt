package uk.co.craigbass.pratura.unit

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.testdouble.StubBasketItemsRetriever
import uk.co.craigbass.pratura.usecase.ViewBasket

class ViewBasketSpec : Spek({
  var basketItems: List<BasketItem> = listOf()
  val basketItemsRetriever = memoized { StubBasketItemsRetriever(basketItems) }
  val viewBasket = memoized { ViewBasket(basketItemsRetriever()) }
  val basketContents = memoized { viewBasket().execute(Unit) }

  given("no lineItems are added to the basket") {
    beforeEachTest {
      basketItems = listOf()
    }

    it("should contain no lineItems") {
      basketContents().lineItems.shouldBeEmpty()
    }

    it("should have a total value of zero") {
      basketContents().basketValue.shouldBe("£0.00")
    }
  }

  given("one aproductsku is in the basket") {
    beforeEachTest {
      basketItems = listOf(BasketItem(1, "aproductsku"))
    }

    it("should contain one line item") {
      basketContents().lineItems.count().shouldBe(1)
    }

    it("should have the correct quantity") {
      basketContents().lineItems.first().quantity.shouldBe(1)
    }

    it("should have the correct sku") {
      basketContents().lineItems.first().sku.shouldBe("aproductsku")
    }
  }

  given("one sku:58371 is in the basket") {
    beforeEachTest {
      basketItems = listOf(BasketItem(1, "sku:58371"))
    }

    it("should have the correct sku") {
      basketContents().lineItems.first().sku.shouldBe("sku:58371")
    }
  }

  given("two sku:58381 is in the basket") {
    beforeEachTest {
      basketItems = listOf(BasketItem(2, "sku:58381"))
    }

    it("should have the correct sku") {
      basketContents().lineItems.first().quantity.shouldBe(2)
    }
  }
})