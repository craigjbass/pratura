package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.BasketItem

interface BasketItemSaver {
  fun save(item: BasketItem)
}
