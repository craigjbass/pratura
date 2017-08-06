package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.domain.BasketItem

interface BasketItemSaver {
  fun save(item: BasketItem)
}
