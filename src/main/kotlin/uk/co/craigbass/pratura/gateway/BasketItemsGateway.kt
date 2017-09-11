package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.BasketReader
import uk.co.craigbass.pratura.usecase.basket.BasketWriter

interface BasketItemsGateway : BasketWriter, BasketReader
