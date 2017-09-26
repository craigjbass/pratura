package uk.co.craigbass.pratura.unit.usecase.basket.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.basket.BasketWriter

class SpyBasketItemsWriter : BasketWriter {
  private val baskets = mutableMapOf<String, MutableList<BasketItem>>()

  override fun new(): String {
    return ""
  }

  override fun save(basketId: String, item: BasketItem) {
    baskets.getOrPut(basketId, ::mutableListOf).add(item)
  }

  fun lastSavedItem(basketId: String): BasketItem? {
    return baskets.getOrPut(basketId, ::mutableListOf).lastOrNull()
  }
}
