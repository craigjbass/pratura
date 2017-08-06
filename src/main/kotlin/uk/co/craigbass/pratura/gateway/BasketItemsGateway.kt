package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.BasketItemsRetriever
import uk.co.craigbass.pratura.usecase.basket.*

interface BasketItemsGateway : BasketItemSaver, BasketItemsRetriever
