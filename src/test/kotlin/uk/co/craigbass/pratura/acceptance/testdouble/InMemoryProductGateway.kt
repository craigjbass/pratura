package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.domain.Product
import uk.co.craigbass.pratura.gateway.ProductGateway

class InMemoryProductGateway : ProductGateway {
  val products : MutableList<Product> = mutableListOf()

  override fun all(): List<Product> {
    return products
  }

  override fun save(product: Product) {
    products.add(product)
  }
}
