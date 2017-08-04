package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.usecase.ViewAllProducts
import uk.co.craigbass.pratura.boundary.usecase.ViewAllProducts.PresentableProduct
import uk.co.craigbass.pratura.domain.*

class ViewAllProducts(private val productRetriever: ProductRetriever,
                      private val currencyRetriever: CurrencyRetriever) : ViewAllProducts {
  lateinit private var currency: Currency

  override fun execute(request: Unit): List<PresentableProduct> {
    currency = currencyRetriever.getCurrentCurrency()
    return getAllProducts().map(this::toPresentableProduct)
  }

  private fun getAllProducts() = productRetriever.all()

  private fun toPresentableProduct(product: Product) = PresentableProduct(
    name = product.name,
    price = currency.format(product.price),
    sku = product.sku
  )
}
