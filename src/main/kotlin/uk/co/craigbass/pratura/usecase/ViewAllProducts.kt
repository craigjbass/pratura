package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewAllProducts
import uk.co.craigbass.pratura.boundary.ViewAllProducts.PresentableProduct
import uk.co.craigbass.pratura.math.toCurrencyWithSymbol

class ViewAllProducts(val productRetriever: ProductRetriever) : ViewAllProducts {
  override fun execute(request: Unit): List<PresentableProduct> {
    return productRetriever.all().map { product ->
      PresentableProduct(product.name, product.price.toCurrencyWithSymbol(), product.sku)
    }
  }
}
