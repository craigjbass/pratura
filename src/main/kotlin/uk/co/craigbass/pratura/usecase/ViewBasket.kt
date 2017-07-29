package uk.co.craigbass.pratura.usecase

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever) {
   fun execute(): PresentableBasket {
       val all = basketItemsGateway.all()
       val items = all.map {}
       return PresentableBasket(items, "£0.00")
    }

    data class PresentableBasket(val items: List<Unit>, val basketValue: String)
}