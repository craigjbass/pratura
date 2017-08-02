package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewAllProducts
import uk.co.craigbass.pratura.boundary.ViewAllProducts.PresentableProduct
import java.math.BigDecimal

class ViewAllProducts(val productRetriever: ProductRetriever) : ViewAllProducts {
  override fun execute(request: Unit): List<PresentableProduct> {
    return productRetriever.all().map { product ->
      PresentableProduct(product.name, "£${product.price.toCurrencyString()}", product.sku)
    }
  }

  private fun BigDecimal.toCurrencyString() = this.setScale(2).toPlainString()
}