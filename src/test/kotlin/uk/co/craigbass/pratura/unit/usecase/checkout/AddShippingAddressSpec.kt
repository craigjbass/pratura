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
  )
  ).forEach {
    given("a shipping address is added ($it)") {
      beforeEachTest {
        AddShippingAddress(shippingAddressSaver())
          .execute(
            Request(
              name = it["name"]!!,
              companyName = it["companyName"]!!,
              addressLine1 = it["addressLine1"]!!,
              addressLine2 = it["addressLine2"],
              addressLine3 = it["addressLine3"],
              city = it["city"]!!,
              province = it["province"]!!,
              zipcode = it["zipcode"]!!
            )
          )
      }

      it("should save the shipping address") {
        savedAddress().name.shouldEqual(it["name"])
        savedAddress().companyName.shouldEqual(it["companyName"])
        savedAddress().addressLine1.shouldEqual(it["addressLine1"])
        savedAddress().addressLine2.shouldEqual(it["addressLine2"])
        savedAddress().addressLine3.shouldEqual(it["addressLine3"])
        savedAddress().city.shouldEqual(it["city"])
        savedAddress().province.shouldEqual(it["province"])
        savedAddress().zipcode.shouldEqual(it["zipcode"])
      }
    }
  }
})
