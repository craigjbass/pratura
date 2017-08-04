package uk.co.craigbass.pratura

import uk.co.craigbass.pratura.main.Pratura
import uk.co.craigbass.pratura.gateway.*
import uk.co.craigbass.pratura.testdouble.*
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.CurrencySetter

class InMemoryPratura : Pratura() {
  val basketGateway: BasketItemsGateway = InMemoryBasketItemsGateway()
  val productGateway: ProductGateway = InMemoryProductGateway()
  val currencyGateway: CurrencyGateway = InMemoryCurrencyGateway()

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
