package uk.co.craigbass.pratura.acceptance

import com.madetech.clean.boundary.executeUseCase
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.acceptance.testdouble.InMemoryPratura
import uk.co.craigbass.pratura.boundary.administration.*
import uk.co.craigbass.pratura.boundary.basket.*
import uk.co.craigbass.pratura.math.toDecimal

class BasketSpec : Spek({
  val pratura = memoized { InMemoryPratura() }
  val basketId = memoized { pratura().executeUseCase(CreateBasket::class).basketId }
  val basketContents = memoized {
    pratura().executeUseCase(
      ViewBasket::class,
      ViewBasket.Request(basketId())
    )
  }

  beforeEachTest {
    pratura().executeUseCase(
      AddProduct::class,
      AddProduct.Request(
        sku = "productsku",
        price = "5.00".toDecimal(),
        name = "Chocolate Bar"
      )
    )
  }

  given("store currency is EUR") {
    beforeEachTest {
      pratura().executeUseCase(
        SetStoreCurrency::class,
        SetStoreCurrency.Request(
          currency = "EUR",
          country = "NL",
          language = "nl"
        )
      )
    }

    given("one item is in the basket") {
      beforeEachTest {
        pratura().executeUseCase(
          AddItemToBasket::class,
          AddItemToBasket.Request(
            basketId = basketId(),
            quantity = 1,
            sku = "productsku"
          )
        )
      }
      val lineItems = memoized { basketContents().lineItems }

      it("should have a total value of € 5,00") {
        basketContents().basketValue.shouldEqual("€5,00")
      }

      it("should have a line item value of € 5,00") {
        lineItems().first().unitPrice.shouldEqual("€5,00")
        lineItems().first().total.shouldEqual("€5,00")
      }
    }
  }

  given("store currency is GBP") {
    beforeEachTest {
      pratura().executeUseCase(
        SetStoreCurrency::class,
        SetStoreCurrency.Request(currency = "GBP", country = "GB", language = "en")
      )
    }

    given("no lineItems are added to the basket") {
      it("should contain no lineItems") {
        basketContents().lineItems.shouldBeEmpty()
      }

      it("should have a total value of £0.00") {
        basketContents().basketValue.shouldEqual("£0.00")
      }
    }

    given("one item is in the basket") {
      beforeEachTest {
        pratura().executeUseCase(
          AddItemToBasket::class,
          AddItemToBasket.Request(
            basketId = basketId(),
            quantity = 1,
            sku = "productsku"
          )
        )
      }
      val lineItems = memoized { basketContents().lineItems }

      it("should contain one item") {
        lineItems().count().shouldBe(1)
      }

      it("should have a total value of £5.00") {
        basketContents().basketValue.shouldEqual("£5.00")
      }

      it("should have the correct sku") {
        lineItems().first().sku.shouldBe("productsku")
      }

      it("should have the correct quantity") {
        lineItems().first().quantity.shouldBe(1)
      }

      it("should have the correct name") {
        lineItems().first().name.shouldEqual("Chocolate Bar")
      }

      it("should have the line item unit price") {
        lineItems().first().unitPrice.shouldEqual("£5.00")
      }

      it("should have the line total price") {
        lineItems().first().total.shouldEqual("£5.00")
      }

      given("we view a new empty basket") {
        it("should have no items") {
          val basket = pratura().executeUseCase(CreateBasket::class)
          val presentableBasket = pratura().executeUseCase(
            ViewBasket::class,
            ViewBasket.Request(basket.basketId)
          )
          presentableBasket.lineItems.count().shouldBe(0)
        }
      }

      given("the same item is added again") {
        beforeEachTest {
          pratura().executeUseCase(
            AddItemToBasket::class,
            AddItemToBasket.Request(
              basketId = basketId(),
              quantity = 1,
              sku = "productsku"
            )
          )
        }

        it("should have quantity of two") {
          lineItems().last().quantity.shouldBe(2)
        }

        it("should only have one line item") {
          lineItems().count().shouldBe(1)
        }

        it("should have the line item unit price") {
          lineItems().first().unitPrice.shouldEqual("£5.00")
        }

        it("should have the line total price") {
          lineItems().first().total.shouldEqual("£10.00")
        }

        it("should have a total value of £5.00") {
          basketContents().basketValue.shouldEqual("£10.00")
        }
      }

      given("a different item is added") {
        beforeEachTest {
          pratura().executeUseCase(
            AddProduct::class,
            AddProduct.Request(
              sku = "sku:1",
              price = "6.24".toDecimal(),
              name = "Picture Frame"
            )
          )

          pratura().executeUseCase(
            AddItemToBasket::class,
            AddItemToBasket.Request(
              basketId = basketId(),
              quantity = 1,
              sku = "sku:1"
            )
          )
        }
        val secondLineItem = memoized { lineItems()[1] }

        it("should have a second line item with name picture frame") {
          secondLineItem().name.shouldEqual("Picture Frame")
        }

        it("should have a second line item with sku sku:1") {
          secondLineItem().sku.shouldEqual("sku:1")
        }

        it("should have a second line item with sku sku:1") {
          secondLineItem().quantity.shouldEqual(1)
        }
      }
    }
  }
})
