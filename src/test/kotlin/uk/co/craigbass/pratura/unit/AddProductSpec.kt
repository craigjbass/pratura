package uk.co.craigbass.pratura.unit

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.boundary.AddProduct.Request
import uk.co.craigbass.pratura.math.toDecimal
import uk.co.craigbass.pratura.testdouble.InMemoryProductGateway
import uk.co.craigbass.pratura.usecase.AddProduct

class AddProductSpec : Spek({

  val productGateway = InMemoryProductGateway()

  beforeEachTest {
    AddProduct(productGateway).execute(Request("sku:1029317", "1.00".toDecimal()))
  }

  it("should have saved one product") {
    productGateway.all().count().shouldEqual(1)
  }

  it("should have the correct sku") {
    productGateway.all().first().sku.shouldEqual("sku:1029317")
  }

  it("should have the correct price") {
    productGateway.all().first().price.shouldEqual("1.00".toDecimal())
  }
})


