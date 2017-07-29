package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemsGateway

class InMemoryBasketItemsGateway : BasketItemsGateway {
    val items = mutableListOf<BasketItem>()

    override fun save(item: BasketItem) {
        items.add(item)
    }

    override fun all(): List<BasketItem> {
        return items
    }
}