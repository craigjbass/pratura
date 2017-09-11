package uk.co.craigbass.pratura.unit.usecase.basket

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.basket.ViewBasket.Request
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.math.toDecimal
import uk.co.craigbass.pratura.unit.testdouble.*
import uk.co.craigbass.pratura.usecase.basket.ViewBasket
import java.math.BigDecimal.ONE

class ViewBasketSpec : Spek({
  var basketId: String? = null
  var currency: Currency? = null
  var products: List<Product> = listOf()
  var basketItems: List<BasketItem> = listOf()
  val basketItemsRetriever = memoized {
    StubBasketReader(mapOf(Pair("an-id-that-exists", basketItems)))
  }
  val currencyRetriever = memoized { StubCurrencyRetriever(currency!!) }
  val viewBasket = memoized {
    ViewBasket(
      basketItemsRetriever(),
      StubProductRetriever(products),
      currencyRetriever()
    )
  }
  val basketContents = memoized {
    viewBasket().execute(
      Request(
        basketId = basketId!!
      )
    )
  }
  val firstLineItem = memoized { basketContents().lineItems.first() }

  beforeEachTest { basketId = "an-id-that-exists" }

  given("a basket that doesn't exist") {
    beforeEachTest {
      currency = Currency("EUR", "NL", "nl")
      basketId = "this-basket-does-not-exist"
    }

    it("should give a validation error") {
      basketContents().errors.`should contain`("BASKET_NOT_FOUND")
    }

    it("should have no items") {
      basketContents().lineItems.shouldBeEmpty()
      basketContents().basketValue.`should equal to`("")
    }
  }

  given("currency is EUR and country is NL") {
    given("one aproductsku is in the basket") {
      beforeEachTest {
        currency = Currency("EUR", "NL", "nl")
        products = listOf(Product("aproductsku", "1.23".toDecimal(), "Watermelon"))
        basketItems = listOf(BasketItem(1, "aproductsku"))
      }

      it("should have no errors") {
        basketContents().errors.shouldBeEmpty()
      }

      it("should have the correct unit price") {
        firstLineItem().unitPrice.shouldEqual("€1,23")
      }

      it("should have the correct line total") {
        firstLineItem().total.shouldEqual("€1,23")
      }

      it("should have the correct basket value") {
        basketContents().basketValue.shouldEqual("€1,23")
      }
    }
  }

  given("currency is GBP and country is GB") {
    beforeEachTest { currency = Currency(currency = "GBP", country = "GB", language = "en") }

    given("no line items are in the basket") {
      beforeEachTest {
        products = listOf()
        basketItems = listOf()
      }
      it("should contain no items") {
        basketContents().lineItems.shouldBeEmpty()
      }

      it("should have a total value of zero") {
        basketContents().basketValue.shouldEqual("£0.00")
      }

      context("and there is a product in the catalogue worth 0.01") {
        beforeEachTest {
          products = listOf(Product("1234", "0.01".toDecimal(), ""))
        }

        it("should have a total value of zero") {
          basketContents().basketValue.shouldEqual("£0.00")
        }
      }
    }


    given("one aproductsku is in the basket") {
      beforeEachTest {
        products = listOf(Product("aproductsku", "1.23".toDecimal(), "Watermelon"))
        basketItems = listOf(BasketItem(1, "aproductsku"))
      }

      it("should contain one line item") {
        basketContents().lineItems.count().shouldBe(1)
      }

      it("should have a total order value of £1.23") {
        basketContents().basketValue.shouldEqual("£1.23")
      }

      it("should have the correct quantity") {
        firstLineItem().quantity.shouldBe(1)
      }

      it("should have the correct sku") {
        firstLineItem().sku.shouldBe("aproductsku")
      }

      it("should have the correct name") {
        firstLineItem().name.shouldBe("Watermelon")
      }

      it("should have the correct unit price") {
        firstLineItem().unitPrice.shouldEqual("£1.23")
      }

      it("should have the correct line total") {
        firstLineItem().total.shouldEqual("£1.23")
      }
    }

    given("one sku:58371 is in the basket") {
      beforeEachTest {
        products = listOf(Product("sku:58371", ONE, ""))
        basketItems = listOf(BasketItem(1, "sku:58371"))
      }

      it("should have the correct sku") {
        firstLineItem().sku.shouldBe("sku:58371")
      }
    }

    given("two sku:58381 is in the basket") {
      beforeEachTest {
        products = listOf(Product("sku:58381", "1.23".toDecimal(), ""))
        basketItems = listOf(BasketItem(2, "sku:58381"))
      }

      it("should have the correct sku") {
        firstLineItem().quantity.shouldBe(2)
      }

      it("should have a basket value of £2.46") {
        basketContents().basketValue.shouldEqual("£2.46")
      }

      it("should have the correct line total") {
        firstLineItem().total.shouldEqual("£2.46")
      }
    }
  }
})

