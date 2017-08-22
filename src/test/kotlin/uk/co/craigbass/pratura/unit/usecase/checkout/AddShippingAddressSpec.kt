package uk.co.craigbass.pratura.unit.usecase.checkout

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress.Request
import uk.co.craigbass.pratura.domain.ShippingAddress
import uk.co.craigbass.pratura.usecase.checkout.*

class AddShippingAddressSpec : Spek({
  class SpyShippingAddressSaver : ShippingAddressSaver {
    lateinit var lastSavedAddress: ShippingAddress

    override fun save(shippingAddress: ShippingAddress) {
      lastSavedAddress = shippingAddress
    }
  }

  val shippingAddressSaver = memoized { SpyShippingAddressSaver() }

  given("a shipping address is added") {
    beforeEachTest {
      AddShippingAddress(shippingAddressSaver()).execute(Request(
        name = "Craig J. Bass",
        companyName = "123 Limited.",
        addressLine1 = "",
        addressLine2 = null,
        addressLine3 = null,
        city = "",
        province = "",
        zipcode = ""
      ))
    }

    it("should save the shipping address") {
      shippingAddressSaver().lastSavedAddress.name.shouldEqual("Craig J. Bass")
      shippingAddressSaver().lastSavedAddress.companyName.shouldEqual("123 Limited.")
    }
  }
})
