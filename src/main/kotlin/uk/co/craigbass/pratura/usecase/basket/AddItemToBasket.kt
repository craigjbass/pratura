package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.usecase.*

class AddItemToBasket(private val basketWriter: BasketWriter,
                      private val basketReader: BasketReader,
                      private val productRetriever: ProductRetriever) : AddItemToBasket {
  lateinit var request: AddItemToBasket.Request

  override fun execute(request: Request): Response {
    this.request = request

    if (`basketNotFound?`()) return basketNotFound()
    if (`isProductPurchasable?`(request.sku)) return productNotFound()

    save(getBasketItemToSave())

    return success()
  }

  private fun productNotFound() = Response(setOf("PRODUCT_NOT_FOUND"))

  private fun `isProductPurchasable?`(sku: String): Boolean {
    return findProduct(sku) == null
  }

  private fun findProduct(sku: String): Product? {
    return productRetriever
      .all()
      .find { product -> product.sku == sku }
  }

  private fun `basketNotFound?`() = !basketReader.`basketExists?`(basketId())

  private fun success() = Response(setOf())

  private fun basketNotFound() = Response(setOf("BASKET_NOT_FOUND"))

  private fun save(basketItem: BasketItem) = basketWriter.save(basketId(), basketItem)

  private fun getBasketItemToSave() = getModifiedBasketItem() ?: newBasketItem()

  private fun newBasketItem() = getModifiedBasketItem(getQuantityToAdd())

  private fun getModifiedBasketItem(): BasketItem? {
    return getMatchingBasketItem()
      ?.let(this::getNewItemQuantity)
      ?.let(this::getModifiedBasketItem)
  }

  private fun allBasketItems() = basketReader.getAll(basketId())

  private fun basketId() = request.basketId

  private fun getNewItemQuantity(basketItem: BasketItem) = basketItem.quantity + getQuantityToAdd()

  private fun getQuantityToAdd() = request.quantity

  private fun getProductSkuToAdd() = request.sku

  private fun getModifiedBasketItem(newQuantity: Int) = BasketItem(quantity = newQuantity, sku = getProductSkuToAdd())

  private fun getMatchingBasketItem() = allBasketItems().find { it.sku == getProductSkuToAdd() }
}
