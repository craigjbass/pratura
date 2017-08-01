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
      AddProduct.Request("productsku", "5.00".toDecimal())
    )
  }

  given("no lineItems are added to the basket") {
    it("should contain no lineItems") {
      basketContents().lineItems.shouldBeEmpty()
    }

    it("should have a total value of £5.00") {
      basketContents().basketValue.shouldEqual("£5.00")
    }
  }

  given("one item is in the basket") {
    beforeEachTest {
      pratura().executeUseCase(
        AddItemToBasket::class,
        AddItemToBasket.Request(1, "productsku")
      )
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

    given("the same item is added again") {
      beforeEachTest {
        pratura().executeUseCase(
          AddItemToBasket::class,
          AddItemToBasket.Request(1, "productsku")
        )
      }

      it("should have quantity of two") {
        lineItems().last().quantity.shouldBe(2)
      }

      it("should only have one line item") {
        lineItems().count().shouldBe(1)
      }
    }
  }
})
