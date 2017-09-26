package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.main.Pratura
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.administration.*
import uk.co.craigbass.pratura.usecase.checkout.*

class InMemoryPratura : Pratura() {
  private val basketGateway = InMemoryBasketItemsGateway()
  private val productGateway = InMemoryProductGateway()
  private val currencyGateway = InMemoryCurrencyGateway()
  private val shippingAddressGateway = InMemoryShippingAddressGateway()

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
  override val basketWriter
    get() = basketGateway
  override val basketReader
    get() = basketGateway
}
