package uk.co.craigbass.pratura.unit

import org.amshove.kluent.shouldNotBeNull
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.AddItemToBasket
import uk.co.craigbass.pratura.usecase.BasketItemSaver

class AddItemToBasketSpec : Spek({

    val basketItemsGateway = memoized { BasketItemsGatewaySpy() }
    val addItemToBasket = AddItemToBasket(basketItemsGateway())

    given("one item is added to basket") {
        beforeEachTest(addItemToBasket::execute)

        it("saves one item to the basket") {
            basketItemsGateway().lastSavedItem().shouldNotBeNull()
        }
    }
})

class BasketItemsGatewaySpy : BasketItemSaver {
    val items = mutableListOf<BasketItem>()

    override fun save(item: BasketItem) {
        items.add(item)
    }

    fun lastSavedItem(): BasketItem {
        return items.last()
    }
}
