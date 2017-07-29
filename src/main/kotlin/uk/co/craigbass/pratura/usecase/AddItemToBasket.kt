package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.AddItemToBasket
import uk.co.craigbass.pratura.boundary.AddItemToBasket.Request
import uk.co.craigbass.pratura.domain.BasketItem

class AddItemToBasket(private val basketItemsGateway: BasketItemSaver) : AddItemToBasket {
    override fun execute(request: Request) {
        basketItemsGateway.save(BasketItem(1, request.sku))
    }
}
