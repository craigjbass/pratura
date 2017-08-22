package uk.co.craigbass.pratura.unit.usecase.checkout

import com.madetech.clean.usecase.execute
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.unit.testdouble.StubBasketItemsRetriever
import uk.co.craigbass.pratura.usecase.checkout.*

class ViewDraftOrderSpec : Spek({
  var basketItems: List<BasketItem>? = null
  var shippingAddress: ShippingAddress? = null
  val viewDraftOrderUseCase = memoized {
    ViewDraftOrder(
      StubBasketItemsRetriever(basketItems!!),
      object : ShippingAddressRetriever {
        override fun getShippingAddress() = shippingAddress
      }
    )
  }
  val response = memoized {
    viewDraftOrderUseCase().execute()
  }

  val presentableShippingAddress = memoized {
    response().shippingAddress!!
  }

  given("there are no items in the basket") {
    beforeEachTest {
      basketItems = listOf()
      shippingAddress = null
    }

    it("should not be ready") {
      response().`isReadyToComplete?`.shouldBeFalse()
    }
  }

  given("there is one item in the basket") {
    beforeEachTest {
      shippingAddress = null
      basketItems = listOf(BasketItem(quantity = 1, sku = "sku"))
    }

    it("should not be ready") {
      response().`isReadyToComplete?`.shouldBeFalse()
    }

    given("there is a shipping address set") {
      beforeEachTest {
        shippingAddress = ShippingAddress(
          name = "John Smith",
          companyName = "Clocks Incorporated"
        )
      }

      it("should be ready") {
        response().`isReadyToComplete?`.shouldBeTrue()
      }

      it("should have a shipping address") {
        presentableShippingAddress().name.shouldEqual("John Smith")
        presentableShippingAddress().companyName.shouldEqual("Clocks Incorporated")
      }
    }

    given("there is another shipping address set") {
      beforeEachTest {
        shippingAddress = ShippingAddress(
          name = "Jane Smith",
          companyName = "Acme Limited."
        )
      }

      it("should have a shipping address") {
        presentableShippingAddress().name.shouldEqual("Jane Smith")
        presentableShippingAddress().companyName.shouldEqual("Acme Limited.")
      }
    }
  }
})

