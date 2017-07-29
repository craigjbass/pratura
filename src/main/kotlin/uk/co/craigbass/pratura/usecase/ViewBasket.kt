package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.PresentableLineItem
import uk.co.craigbass.pratura.boundary.ViewBasket.PresentableBasket

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever) : ViewBasket {
   override fun execute(request: Unit): PresentableBasket {
       val all = basketItemsGateway.all()
       val items = all.map {
           PresentableLineItem(1, it.sku)
       }
       return PresentableBasket(items, "£0.00")
    }
}