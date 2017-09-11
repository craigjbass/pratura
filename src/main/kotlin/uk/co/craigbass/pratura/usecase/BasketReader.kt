package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.BasketItem

interface BasketReader {
  fun getAll(basketId: String): List<BasketItem>
  fun `basketExists?`(basketId: String): Boolean
}
