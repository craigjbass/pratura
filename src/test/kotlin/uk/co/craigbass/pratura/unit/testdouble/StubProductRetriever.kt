package uk.co.craigbass.pratura.unit.testdouble

import uk.co.craigbass.pratura.domain.Product
import uk.co.craigbass.pratura.usecase.ProductRetriever

class StubProductRetriever(private val products: List<Product>) : ProductRetriever {
  override fun all(): List<Product> = this.products
}
