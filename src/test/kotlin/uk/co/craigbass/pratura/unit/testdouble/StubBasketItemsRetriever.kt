package uk.co.craigbass.pratura.unit.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

class StubBasketItemsRetriever(val basketItems: List<BasketItem>) : BasketItemsRetriever {
  override fun getAll(): List<BasketItem> = basketItems
}
