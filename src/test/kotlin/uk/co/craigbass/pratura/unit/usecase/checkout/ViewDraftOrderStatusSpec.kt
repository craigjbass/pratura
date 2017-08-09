package uk.co.craigbass.pratura.unit.usecase.checkout

import com.madetech.clean.usecase.execute
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.unit.testdouble.StubBasketItemsRetriever
import uk.co.craigbass.pratura.usecase.checkout.*

class ViewDraftOrderStatusSpec : Spek({
  var basketItems : List<BasketItem>? = null
  var shippingAddress : ShippingAddress? = null

  val viewDraftOrderStatusUseCase = memoized {
    ViewDraftOrderStatus(
      StubBasketItemsRetriever(basketItems!!),
      object : ShippingAddressRetriever { override fun getShippingAddress() = shippingAddress }
    )
  }

  given("there are no items in the basket") {
    beforeEachTest {
      basketItems = listOf()
      shippingAddress = null
    }

    it("should not be ready") {
      viewDraftOrderStatusUseCase().execute().isReady.shouldBeFalse()
    }
  }

  given("there is one item in the basket") {
    beforeEachTest {
      shippingAddress = null
      basketItems = listOf(BasketItem(quantity = 1, sku = "sku"))
    }

    it("should not be ready") {
      viewDraftOrderStatusUseCase().execute().isReady.shouldBeFalse()
    }

    given("there is a shipping address set") {
      beforeEachTest { shippingAddress = ShippingAddress(name = "") }

      it("should be ready") {
        viewDraftOrderStatusUseCase().execute().isReady.shouldBeTrue()
      }
    }
  }
})

