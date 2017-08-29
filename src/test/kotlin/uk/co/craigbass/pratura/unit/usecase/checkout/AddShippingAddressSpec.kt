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
  val savedAddress = memoized { shippingAddressSaver().lastSavedAddress }

  listOf(mapOf(
    "name" to "Craig J. Bass",
    "companyName" to "123 Limited.",
    "addressLine1" to "Mango Grove Way",
    "addressLine2" to null,
    "addressLine3" to null,
    "city" to "",
    "province" to "",
    "zipcode" to ""
  ), mapOf(
    "name" to "John Turner",
    "companyName" to "Lexico Inc.",
    "addressLine1" to "Lexway",
    "addressLine2" to null,
    "addressLine3" to null,
    "city" to "",
    "province" to "",
    "zipcode" to ""
  ), mapOf(
    "name" to null,
    "companyName" to null,
    "addressLine1" to null,
    "addressLine2" to null,
    "addressLine3" to null,
    "city" to null,
    "province" to null,
    "zipcode" to null
  )).forEach { addressExample ->
    given("a shipping address is added ($addressExample)") {
      beforeEachTest {
        AddShippingAddress(shippingAddressSaver())
          .execute(
            Request(
              name = addressExample["name"],
              companyName = addressExample["companyName"],
              addressLine1 = addressExample["addressLine1"],
              addressLine2 = addressExample["addressLine2"],
              addressLine3 = addressExample["addressLine3"],
              city = addressExample["city"],
              province = addressExample["province"],
              zipcode = addressExample["zipcode"]
            )
          )
      }

      it("should save the shipping address") {
        savedAddress().name.shouldEqual(addressExample["name"] ?: "")
        savedAddress().companyName.shouldEqual(addressExample["companyName"])
        savedAddress().addressLine1.shouldEqual(addressExample["addressLine1"] ?: "")
        savedAddress().addressLine2.shouldEqual(addressExample["addressLine2"])
        savedAddress().addressLine3.shouldEqual(addressExample["addressLine3"])
        savedAddress().city.shouldEqual(addressExample["city"] ?: "")
        savedAddress().province.shouldEqual(addressExample["province"] ?: "")
        savedAddress().zipcode.shouldEqual(addressExample["zipcode"] ?: "")
      }
    }
  }
})
