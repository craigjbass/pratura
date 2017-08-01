package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever) : ViewBasket {
  override fun execute(request: Unit): PresentableBasket {
    val all = basketItemsGateway.all()
    val items = all.map {
      PresentableLineItem(it.quantity, it.sku)
    }
    return PresentableBasket(items, "£0.00")
  }
}