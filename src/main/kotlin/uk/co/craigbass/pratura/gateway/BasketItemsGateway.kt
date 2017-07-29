package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.BasketItemSaver
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

interface BasketItemsGateway : BasketItemSaver, BasketItemsRetriever