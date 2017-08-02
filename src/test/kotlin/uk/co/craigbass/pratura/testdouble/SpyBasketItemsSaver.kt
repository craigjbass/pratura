package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemSaver

class SpyBasketItemsSaver : BasketItemSaver {
  val items = mutableListOf<BasketItem>()

  override fun save(item: BasketItem) {
    items.add(item)
  }

  fun lastSavedItem(): BasketItem {
    return items.last()
  }
}
