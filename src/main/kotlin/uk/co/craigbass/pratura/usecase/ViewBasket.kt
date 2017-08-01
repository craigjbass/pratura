package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever,
                 private val productRetriever: ProductRetriever) : ViewBasket {
  override fun execute(request: Unit): PresentableBasket {
    val all = basketItemsGateway.all()
    val items = all.map {
      PresentableLineItem(it.quantity, it.sku)
    }
    return PresentableBasket(
      items,
      "£${basketValue()}"
    )
  }

  private fun basketValue(): String? {
    val products = productRetriever.all()

    if(products.count() < 1) return "0.00"
    return products.first().price.toPlainString()
  }
}
