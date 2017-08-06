package uk.co.craigbass.pratura.unit.usecase.administration

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.acceptance.testdouble.InMemoryProductGateway
import uk.co.craigbass.pratura.boundary.administration.AddProduct.Request
import uk.co.craigbass.pratura.math.toDecimal
import uk.co.craigbass.pratura.usecase.administration.AddProduct

class AddProductSpec : Spek({
  val productGateway = InMemoryProductGateway()
  val useCase = memoized { AddProduct(productGateway) }
  beforeEachTest {
    useCase().execute(Request(
      sku = "sku:1029317",
      price = "6.99".toDecimal(),
      name = "Pack of 4 AA Batteries"
    ))
  }

  val savedProducts = memoized { productGateway.all() }
  val firstSavedProduct = memoized { savedProducts().first() }

  it("should have saved one product") {
    savedProducts().count().shouldEqual(1)
  }

  it("should have the correct sku") {
    firstSavedProduct().sku.shouldEqual("sku:1029317")
  }

  it("should have the correct price") {
    firstSavedProduct().price.shouldEqual("6.99".toDecimal())
  }

  it("should have the correct name") {
    firstSavedProduct().name.shouldEqual("Pack of 4 AA Batteries")
  }
})
