package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.AddItemToBasket.AddItemToBasketRequest
import uk.co.craigbass.pratura.domain.BasketItem

class AddItemToBasket(private val basketItemsGateway: BasketItemSaver) {
    fun execute(request: AddItemToBasketRequest) {
        basketItemsGateway.save(BasketItem(1, request.sku))
    }
}
