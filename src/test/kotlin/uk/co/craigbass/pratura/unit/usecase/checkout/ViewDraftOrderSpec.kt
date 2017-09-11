package uk.co.craigbass.pratura.unit.usecase.checkout

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.Request
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.unit.testdouble.SpyBasketReader
import uk.co.craigbass.pratura.usecase.checkout.*

typealias TestCase<A, B> = Pair<A, B>

class ViewDraftOrderSpec : Spek({
  var baskets: Map<String, List<BasketItem>>? = null
  var shippingAddress: ShippingAddress? = null
  val basketReader = memoized { SpyBasketReader(baskets!!) }
  val viewDraftOrderUseCase = memoized {
    ViewDraftOrder(
      basketReader(),
      object : ShippingAddressRetriever {
        override fun getShippingAddress() = shippingAddress
      }
    )
  }
  val response = memoized {
    viewDraftOrderUseCase().execute(
      Request("an-id-that-exists")
    )
  }
  val presentableShippingAddress = memoized {
    response().shippingAddress!!
  }

  given("one basket") {
    beforeEachTest {
      baskets = mapOf(
        Pair("this-is-a-valid-id", listOf())
      )
    }

    context("when viewing a draft order for a basket that does exist") {
      beforeEachTest {
        viewDraftOrderUseCase().execute(
          Request("this-is-a-valid-id")
        )
      }

      it("should have called the basket reader with the correct id") {
        basketReader().basketIdsSentToBasketExists.shouldContain("this-is-a-valid-id")
      }
    }

    context("when viewing draft order for a basket that does not exist") {
      val notFoundResponse = memoized {
        viewDraftOrderUseCase().execute(
          Request("basket that does not exist")
        )
      }

      beforeEachTest { notFoundResponse() }

      it("should have called the basket reader with the correct id") {
        basketReader().basketIdsSentToBasketExists.shouldContain("basket that does not exist")
      }

      it("should be a basket not found error") {
        notFoundResponse().errors.shouldContain("BASKET_NOT_FOUND")
      }
      it("should not be ready") {
        notFoundResponse().`readyToComplete?`.shouldBeFalse()
      }
    }
  }

  given("there are no items in the basket") {
    beforeEachTest {
      baskets = mapOf(Pair("an-id-that-exists", listOf()))
      shippingAddress = null
    }

    it("should not be ready") {
      response().`readyToComplete?`.shouldBeFalse()
    }

    it("should have no errors") {
      response().errors.shouldBeEmpty()
    }
  }

  given("there is one item in the basket") {
    beforeEachTest {
      shippingAddress = null
      baskets = mapOf(Pair("an-id-that-exists", listOf(BasketItem(quantity = 1, sku = "sku"))))
    }

    it("should not be ready") {
      response().`readyToComplete?`.shouldBeFalse()
    }

    it("should have no errors") {
      response().errors.shouldBeEmpty()
    }

    listOf(mapOf(
      "name" to "John Smith",
      "companyName" to "Clocks Incorporated",
      "addressLine1" to "1 The Street",
      "addressLine2" to null,
      "addressLine3" to null,
      "city" to "East Preston",
      "province" to "West Sussex",
      "zipcode" to "BR00 0AA"
    ), mapOf(
      "name" to "Jane Smith",
      "companyName" to "Acme Limited.",
      "addressLine1" to "90 Generic Lane",
      "addressLine2" to "Hogsmead",
      "addressLine3" to "Slight Left",
      "city" to "Broadgate",
      "province" to "Kent",
      "zipcode" to "CT00 0FW"
    )).map { address ->
      TestCase(
        ShippingAddress(
          address["name"]!!,
          address["companyName"]!!,
          address["addressLine1"]!!,
          address["addressLine2"],
          address["addressLine3"],
          address["city"]!!,
          address["province"]!!,
          address["zipcode"]!!
        ),
        address.mapValues { it -> it.value ?: "" }
      )
    }.forEach { (address, expectedAddress) ->
      given("there is a shipping address set ($expectedAddress)") {
        beforeEachTest { shippingAddress = address }

        it("should be ready") {
          response().`readyToComplete?`.shouldBeTrue()
        }

        it("should have a shipping address") {
          presentableShippingAddress().name.shouldEqual(expectedAddress["name"])
          presentableShippingAddress().companyName.shouldEqual(expectedAddress["companyName"])
          presentableShippingAddress().addressLine1.shouldEqual(expectedAddress["addressLine1"])
          presentableShippingAddress().addressLine2.shouldEqual(expectedAddress["addressLine2"])
          presentableShippingAddress().addressLine3.shouldEqual(expectedAddress["addressLine3"])
          presentableShippingAddress().city.shouldEqual(expectedAddress["city"])
          presentableShippingAddress().province.shouldEqual(expectedAddress["province"])
          presentableShippingAddress().zipcode.shouldEqual(expectedAddress["zipcode"])
        }
      }
    }
  }
})

