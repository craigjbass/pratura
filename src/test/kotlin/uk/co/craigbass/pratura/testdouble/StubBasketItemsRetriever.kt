package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

class StubBasketItemsRetriever(val basketItems: List<BasketItem>) : BasketItemsRetriever {
  override fun all(): List<BasketItem> = basketItems
}