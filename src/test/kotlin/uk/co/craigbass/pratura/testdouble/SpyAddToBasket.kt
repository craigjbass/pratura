package uk.co.craigbass.pratura.testdouble

import uk.co.craigbass.pratura.boundary.AddItemToBasket

class SpyAddToBasket : AddItemToBasket {
    val requestsReceived: MutableList<AddItemToBasket.Request> = mutableListOf()

    @Synchronized override fun execute(request: AddItemToBasket.Request) {
        requestsReceived.add(request)
    }
}