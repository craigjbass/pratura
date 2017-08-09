package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket.Request
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

class AddItemToBasket(private val basketItemSaver: BasketItemSaver,
                      private val basketItemsRetriever: BasketItemsRetriever) : AddItemToBasket {
  var request: AddItemToBasket.Request? = null

  override fun execute(request: Request) {
    this.request = request
    save(getBasketItemToSave())
  }

  private fun save(basketItem: BasketItem) = basketItemSaver.save(basketItem)

  private fun getBasketItemToSave() = getModifiedBasketItem() ?: newBasketItem()

  private fun newBasketItem() = getModifiedBasketItem(getQuantityToAdd())

  private fun getModifiedBasketItem(): BasketItem? {
    return getMatchingBasketItem()
      ?.let(this::getNewItemQuantity)
      ?.let(this::getModifiedBasketItem)
  }

  private fun allBasketItems() = basketItemsRetriever.getAll()

  private fun getNewItemQuantity(basketItem: BasketItem) = basketItem.quantity + getQuantityToAdd()

  private fun getQuantityToAdd() = request!!.quantity

  private fun getProductSkuToAdd() = request!!.sku

  private fun getModifiedBasketItem(newQuantity: Int) = BasketItem(quantity = newQuantity, sku = getProductSkuToAdd())

  private fun getMatchingBasketItem() = allBasketItems().find { it.sku == getProductSkuToAdd() }
}