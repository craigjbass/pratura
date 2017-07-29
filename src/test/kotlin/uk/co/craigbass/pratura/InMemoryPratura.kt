package uk.co.craigbass.pratura

import uk.co.craigbass.pratura.boundary.Pratura
import uk.co.craigbass.pratura.gateway.BasketItemsGateway
import uk.co.craigbass.pratura.testdouble.InMemoryBasketItemsGateway

class InMemoryPratura : Pratura() {
    val basketGateway: BasketItemsGateway = InMemoryBasketItemsGateway()

    override val basketItemSaver
        get() = basketGateway
    override val basketItemsRetriever
        get() = basketGateway
}