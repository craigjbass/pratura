package uk.co.craigbass.pratura.acceptance

import com.madetech.clean.boundary.executeUseCase
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.acceptance.testdouble.InMemoryPratura
import uk.co.craigbass.pratura.boundary.administration.AddProduct
import uk.co.craigbass.pratura.boundary.basket.*
import uk.co.craigbass.pratura.boundary.checkout.*
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.Request
import uk.co.craigbass.pratura.math.toDecimal

class CheckoutSpec : Spek({
  val pratura = memoized { InMemoryPratura() }
  val basketId = memoized { pratura().executeUseCase(CreateBasket::class).basketId }
  val draftOrder = memoized {
    pratura().executeUseCase(
      ViewDraftOrder::class,
      Request(
        basketId = basketId()
      )
    )
  }

  given("nothing is in the basket") {
    it("should not be ready") {
      draftOrder().`readyToComplete?`.shouldBeFalse()
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
        AddItemToBasket.Request(
          basketId = basketId(),
          quantity = 1,
          sku = "abcdefg"
        )
      )
    }

    context("when there is no shipping address") {
      it("should not be ready") {
        draftOrder().`readyToComplete?`.shouldBeFalse()
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
      val presentableShippingAddress = memoized { draftOrder().shippingAddress!! }

      it("should be ready") {
        draftOrder().`readyToComplete?`.shouldBeTrue()
      }

      it("should have a shipping address") {
        presentableShippingAddress().name.shouldEqual("Craig J. Bass")
        presentableShippingAddress().companyName.shouldEqual("Pratura Inc.")
        presentableShippingAddress().addressLine1.shouldEqual("1 Pratura Way")
        presentableShippingAddress().addressLine2.shouldEqual("")
        presentableShippingAddress().addressLine3.shouldEqual("")
        presentableShippingAddress().city.shouldEqual("Vertiform City")
        presentableShippingAddress().province.shouldEqual("Sector 001")
        presentableShippingAddress().zipcode.shouldEqual("123456")
      }
    }
  }
})
