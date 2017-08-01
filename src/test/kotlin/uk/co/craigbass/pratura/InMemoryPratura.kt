package uk.co.craigbass.pratura

import uk.co.craigbass.pratura.boundary.Pratura
import uk.co.craigbass.pratura.gateway.*
import uk.co.craigbass.pratura.testdouble.*
import uk.co.craigbass.pratura.usecase.*

class InMemoryPratura : Pratura() {
  val basketGateway: BasketItemsGateway = InMemoryBasketItemsGateway()
  val productGateway: ProductGateway = InMemoryProductGateway()

  override val productRetriever: ProductRetriever
    get() = productGateway
  override val productSaver: ProductSaver
    get() = productGateway

  override val basketItemSaver
    get() = basketGateway
  override val basketItemsRetriever
    get() = basketGateway
}