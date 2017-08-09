package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.gateway.BasketItemsGateway

class InMemoryBasketItemsGateway : BasketItemsGateway {
  val items = mutableListOf<BasketItem>()

  override fun save(item: BasketItem) {
    items.removeIf { it.sku == item.sku }
    items.add(item)
  }

  override fun getAll(): List<BasketItem> {
    return items
  }
}