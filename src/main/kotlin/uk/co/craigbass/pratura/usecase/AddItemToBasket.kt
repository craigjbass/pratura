package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.BasketItem

class AddItemToBasket(private val basketItemsGateway: BasketItemSaver) {
    fun execute() {
        basketItemsGateway.save(BasketItem())
    }
}
