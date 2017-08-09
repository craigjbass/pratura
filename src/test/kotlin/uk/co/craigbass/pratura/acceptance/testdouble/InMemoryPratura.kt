package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.main.Pratura
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.administration.*
import uk.co.craigbass.pratura.usecase.checkout.*

class InMemoryPratura : Pratura() {
  val basketGateway = InMemoryBasketItemsGateway()
  val productGateway = InMemoryProductGateway()
  val currencyGateway = InMemoryCurrencyGateway()
  val shippingAddressGateway = InMemoryShippingAddressGateway()

  override val shippingAddressSaver: ShippingAddressSaver
    get() = shippingAddressGateway
  override val shippingAddressRetriever: ShippingAddressRetriever
    get() = shippingAddressGateway

  override val currencySetter: CurrencySetter
    get() = currencyGateway
  override val currencyRetriever: CurrencyRetriever
    get() = currencyGateway

  override val productRetriever: ProductRetriever
    get() = productGateway
  override val productSaver: ProductSaver
    get() = productGateway

  override val basketItemSaver
    get() = basketGateway
  override val basketItemsRetriever
    get() = basketGateway
}
