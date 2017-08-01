package uk.co.craigbass.pratura.unit

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.math.toDecimal
import uk.co.craigbass.pratura.testdouble.*
import uk.co.craigbass.pratura.usecase.ViewBasket

class ViewBasketSpec : Spek({
  var products: List<Product> = listOf()
  var basketItems: List<BasketItem> = listOf()
  val basketItemsRetriever = memoized { StubBasketItemsRetriever(basketItems) }
  val viewBasket = memoized {
    ViewBasket(
      basketItemsRetriever(),
      StubProductRetriever(products)
    )
  }
  val basketContents = memoized { viewBasket().execute(Unit) }

  given("no lineItems are added to the basket") {
    it("should contain no lineItems") {
      basketContents().lineItems.shouldBeEmpty()
    }

    it("should have a total value of zero") {
      basketContents().basketValue.shouldEqual("£0.00")
    }
  }

  given("one aproductsku is in the basket") {
    beforeEachTest {
      products = listOf(Product("aproductsku", "1.23".toDecimal()))
      basketItems = listOf(BasketItem(1, "aproductsku"))
    }

    it("should contain one line item") {
      basketContents().lineItems.count().shouldBe(1)
    }

    it("should have a total order value of £1.23") {
      basketContents().basketValue.shouldEqual("£1.23")
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
