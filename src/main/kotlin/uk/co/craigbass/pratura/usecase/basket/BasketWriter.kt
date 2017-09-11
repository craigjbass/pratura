package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.basket.CreateBasket.BasketInitialiser

interface BasketWriter : BasketInitialiser {
  fun save(basketId: String, item: BasketItem)
}
