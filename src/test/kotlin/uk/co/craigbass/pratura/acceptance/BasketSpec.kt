package uk.co.craigbass.pratura.acceptance

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.InMemoryPratura
import uk.co.craigbass.pratura.boundary.*
import uk.co.craigbass.pratura.math.toDecimal

class BasketSpec : Spek({
  val pratura = memoized { InMemoryPratura() }
  val basketContents = memoized { pratura().executeUseCase(ViewBasket::class, Unit) }

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

    given("the same item is added again") {
      beforeEachTest {
        pratura().executeUseCase(
          AddItemToBasket::class,
          AddItemToBasket.Request(
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
})
