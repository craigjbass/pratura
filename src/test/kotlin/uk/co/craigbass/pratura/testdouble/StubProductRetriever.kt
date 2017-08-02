package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.domain.Product
import uk.co.craigbass.pratura.usecase.ProductRetriever

class StubProductRetriever(val products: List<Product>) : ProductRetriever {
  override fun all(): List<Product> = this.products
}
