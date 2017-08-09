package uk.co.craigbass.pratura.main

import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.gateway.*
import uk.co.craigbass.pratura.http.*
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.administration.*
import uk.co.craigbass.pratura.usecase.basket.BasketItemSaver
import uk.co.craigbass.pratura.usecase.checkout.*

fun main(args: Array<String>) {
  val webServer = WebServer()
  webServer.addController(AddToBasketController(StandInPratura()))
  webServer.start()
}

class StandInPratura : Pratura(),
                       BasketItemsGateway,
                       ProductSaver,
                       CurrencyGateway,
                       ShippingAddressRetriever,
                       ShippingAddressSaver {
  override val shippingAddressRetriever: ShippingAddressRetriever
    get() = this

  override val shippingAddressSaver: ShippingAddressSaver
    get() = this
  override val currencyRetriever: CurrencyRetriever
    get() = this
  override val currencySetter: CurrencySetter
    get() = this
  override val productRetriever: ProductRetriever
    get() = StandInProductRetriever()
  override val productSaver: ProductSaver
    get() = this
  override val basketItemSaver: BasketItemSaver
    get() = this
  override val basketItemsRetriever: BasketItemsRetriever
    get() = this
  class StandInProductRetriever : ProductRetriever {

    override fun all(): List<Product> = listOf()
  }
  override fun set(currency: Currency) = Unit

  override fun getCurrentCurrency(): Currency {
    return Currency("GBP", "GB", "")
  }

  override fun getAll(): List<BasketItem> = listOf()

  override fun getShippingAddress(): ShippingAddress? = null

  override fun save(product: Product) = Unit
  override fun save(item: BasketItem) = Unit
  override fun save(shippingAddress: ShippingAddress) = Unit
}
