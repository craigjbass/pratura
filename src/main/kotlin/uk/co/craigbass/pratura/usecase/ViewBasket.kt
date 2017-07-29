package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket.PresentableBasket

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever) {
   fun execute(): PresentableBasket {
       val all = basketItemsGateway.all()
       val items = all.map {}
       return PresentableBasket(items, "£0.00")
    }
}