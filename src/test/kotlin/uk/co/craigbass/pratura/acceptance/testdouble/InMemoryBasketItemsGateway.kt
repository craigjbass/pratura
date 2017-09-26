package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.gateway.BasketItemsGateway

class InMemoryBasketItemsGateway : BasketItemsGateway {
  private val baskets = mutableMapOf<String, MutableList<BasketItem>>()

  override fun new(): String {
    val uniqueBasketId = uniqueBasketId()
    baskets.put(uniqueBasketId, mutableListOf())
    return uniqueBasketId
  }

  private fun uniqueBasketId(): String {
    var uniqueBasketId: String
    do {
      uniqueBasketId = Math.random().toString()
    } while (baskets.contains(uniqueBasketId))
    return uniqueBasketId
  }

  override fun save(basketId: String, item: BasketItem) {
    val items = items(basketId)
    items.removeIf { it.sku == item.sku }
    items.add(item)
  }

  override fun `basketExists?`(basketId: String) = baskets.containsKey(basketId)

  override fun getAll(basketId: String): List<BasketItem> {
    return items(basketId)
  }

  private fun items(basketId: String) = baskets[basketId]!!
}
