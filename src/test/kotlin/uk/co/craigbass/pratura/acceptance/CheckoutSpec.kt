package uk.co.craigbass.pratura.acceptance

import com.madetech.clean.boundary.executeUseCase
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.acceptance.testdouble.InMemoryPratura
import uk.co.craigbass.pratura.boundary.administration.AddProduct
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket
import uk.co.craigbass.pratura.boundary.checkout.*
import uk.co.craigbass.pratura.math.toDecimal

class CheckoutSpec : Spek({
  val pratura = memoized { InMemoryPratura() }
  val draftOrder = memoized { pratura().executeUseCase(ViewDraftOrder::class) }

  given("nothing is in the basket") {
    it("should not be ready") {
      draftOrder().`isReadyToComplete?`.shouldBeFalse()
    }
  }

  given("a product is in the catalogue and the basket") {
    beforeEachTest {
      pratura().executeUseCase(
        AddProduct::class,
        AddProduct.Request(
          sku = "abcdefg",
          price = "2.99".toDecimal(),
          name = "Anti-Bacterial Spray"
        )
      )

      pratura().executeUseCase(
        AddItemToBasket::class,
        AddItemToBasket.Request(quantity = 1, sku = "abcdefg")
      )
    }

    context("when there is no shipping address") {
      it("should not be ready") {
        draftOrder().`isReadyToComplete?`.shouldBeFalse()
      }

      it("should have no shipping address") {
        draftOrder().shippingAddress.shouldBeNull()
      }
    }

    context("when there is a shipping address") {
      beforeEachTest {
        pratura().executeUseCase(
          AddShippingAddress::class,
          AddShippingAddress.Request(
            name = "Craig J. Bass",
            companyName = "Pratura Inc.",
            addressLine1 = "1 Pratura Way",
            addressLine2 = null,
            addressLine3 = null,
            city = "Vertiform City",
            province = "Sector 001",
            zipcode = "123456"
          )
        )
      }

      it("should be ready") {
        draftOrder().`isReadyToComplete?`.shouldBeTrue()
      }

      it("should have a shipping address") {
        draftOrder().shippingAddress!!.name.shouldEqual("Craig J. Bass")
        draftOrder().shippingAddress!!.companyName.shouldEqual("Pratura Inc.")
      }
    }
  }
})
