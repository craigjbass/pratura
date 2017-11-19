package uk.co.craigbass.pratura.main

import com.madetech.clean.Application
import com.madetech.clean.usecase.*
import uk.co.craigbass.pratura.boundary.administration.AddProduct
import uk.co.craigbass.pratura.boundary.administration.SetStoreCurrency
import uk.co.craigbass.pratura.boundary.basket.*
import uk.co.craigbass.pratura.boundary.catalogue.ViewAllProducts
import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.administration.*
import uk.co.craigbass.pratura.usecase.basket.BasketWriter
import uk.co.craigbass.pratura.usecase.checkout.*
import kotlin.reflect.KClass

abstract class Pratura : Application() {
  abstract val basketWriter: BasketWriter
  abstract val basketReader: BasketReader
  abstract val productSaver: ProductSaver
  abstract val productRetriever: ProductRetriever
  abstract val currencySetter: CurrencySetter
  abstract val currencyRetriever: CurrencyRetriever
  abstract val shippingAddressRetriever: ShippingAddressRetriever
  abstract val  shippingAddressSaver: ShippingAddressSaver

  override fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>? = null

  override fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>? {
    return when (useCase) {
      AddItemToBasket::class -> uk.co.craigbass.pratura.usecase.basket.AddItemToBasket(
        basketWriter,
        basketReader,
        productRetriever
      )
      ViewBasket::class -> uk.co.craigbass.pratura.usecase.basket.ViewBasket(
        basketReader,
        productRetriever,
        currencyRetriever
      )
      CreateBasket::class -> uk.co.craigbass.pratura.usecase.basket.CreateBasket(basketWriter)
      AddProduct::class -> uk.co.craigbass.pratura.usecase.administration.AddProduct(productSaver)
      ViewAllProducts::class -> uk.co.craigbass.pratura.usecase.catalogue.ViewAllProducts(
        productRetriever,
        currencyRetriever
      )
      AddShippingAddress::class -> uk.co.craigbass.pratura.usecase.checkout.AddShippingAddress(
        shippingAddressSaver
      )
      ViewDraftOrder::class -> uk.co.craigbass.pratura.usecase.checkout.ViewDraftOrder(
        basketReader,
        shippingAddressRetriever
      )
      SetStoreCurrency::class -> uk.co.craigbass.pratura.usecase.administration.SetStoreCurrency(currencySetter)
      else -> throw Exception("Use Case Not Found")
    }
  }
}
