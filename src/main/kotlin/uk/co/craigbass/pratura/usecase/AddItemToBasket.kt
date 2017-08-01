package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.AddItemToBasket
import uk.co.craigbass.pratura.boundary.AddItemToBasket.Request
import uk.co.craigbass.pratura.domain.BasketItem

class AddItemToBasket(private val basketItemSaver: BasketItemSaver,
                      private val basketItemsRetriever: BasketItemsRetriever) : AddItemToBasket {
  override fun execute(request: Request) {
    val let: BasketItem? = basketItemsRetriever.all().find { it.sku == request.sku }?.let {
      BasketItem(it.quantity + request.quantity, it.sku)
    }
    val basketItem = let ?: BasketItem(1, request.sku)

    basketItemSaver.save(basketItem)
  }
}
