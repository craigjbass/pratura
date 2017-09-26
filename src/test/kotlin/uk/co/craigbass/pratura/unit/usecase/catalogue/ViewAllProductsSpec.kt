package uk.co.craigbass.pratura.unit.usecase.catalogue

import com.madetech.clean.usecase.execute
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.unit.testdouble.*
import uk.co.craigbass.pratura.usecase.catalogue.ViewAllProducts
import java.math.BigDecimal.ONE

class ViewAllProductsSpec : Spek({
  var products: List<Product> = listOf()
  val currency = Currency("GBP", "GB", "en")
  val productRetriever = memoized { StubProductRetriever(products) }
  val useCase = memoized {
    ViewAllProducts(productRetriever(),
      StubCurrencyRetriever(currency))
  }

  given("one product is available") {
    beforeEachTest {
      products = listOf(
        Product(
          sku = "sku:90",
          price = ONE,
          name = "Bottle of Water"
        )
      )
    }
    val presentableProducts = memoized { useCase().execute() }
    val firstPresentableProduct = memoized { presentableProducts().first() }

    it("should return one product") {
      presentableProducts().count().shouldEqual(1)
    }

    it("should have the correct sku") {
      firstPresentableProduct().sku.shouldEqual("sku:90")
    }

    it("should have the correct price") {
      firstPresentableProduct().price.shouldEqual("£1.00")
    }

    it("should have the correct name") {
      firstPresentableProduct().name.shouldEqual("Bottle of Water")
    }
  }
})
