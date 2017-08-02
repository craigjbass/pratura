package uk.co.craigbass.pratura.acceptance

import com.madetech.clean.boundary.executeUseCase
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.InMemoryPratura
import uk.co.craigbass.pratura.boundary.*
import uk.co.craigbass.pratura.math.toDecimal

class ProductCatalogueSpec : Spek({
  val pratura = memoized { InMemoryPratura() }

  beforeEachTest {
    pratura().executeUseCase(
      AddProduct::class,
      AddProduct.Request("sku:1", "9.99".toDecimal(), "Fancy Box of Chocolates")
    )
  }

  val productList = memoized { pratura().executeUseCase(ViewAllProducts::class) }

  it("should have the correct sku") { productList().first().sku.shouldEqual("sku:1") }
  it("should have the correct price") { productList().first().price.shouldEqual("£9.99") }
  it("should have the correct name") { productList().first().name.shouldEqual("Fancy Box of Chocolates") }
})
