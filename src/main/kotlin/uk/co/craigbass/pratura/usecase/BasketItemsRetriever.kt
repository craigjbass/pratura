package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.BasketItem

interface BasketItemsRetriever {
  fun all(): List<BasketItem>
}