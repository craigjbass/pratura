package uk.co.craigbass.pratura.unit.testdouble

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketReader

typealias Baskets = Map<String, List<BasketItem>>

open class StubBasketReader(
  private val baskets: Baskets
) : BasketReader {
  override fun getAll(basketId: String): List<BasketItem> = baskets[basketId]!!

  override fun `basketExists?`(basketId: String) = baskets.keys.contains(basketId)
}

