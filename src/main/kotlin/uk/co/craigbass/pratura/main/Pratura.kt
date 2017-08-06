package uk.co.craigbass.pratura.main

import com.madetech.clean.Application
import com.madetech.clean.usecase.*
import uk.co.craigbass.pratura.boundary.administration.AddProduct
import uk.co.craigbass.pratura.boundary.administration.SetStoreCurrency
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket
import uk.co.craigbass.pratura.boundary.basket.ViewBasket
import uk.co.craigbass.pratura.boundary.catalogue.ViewAllProducts
import uk.co.craigbass.pratura.boundary.checkout.*
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.administration.*
import uk.co.craigbass.pratura.usecase.basket.*
import uk.co.craigbass.pratura.usecase.checkout.ShippingAddressRetriever
import kotlin.reflect.KClass

abstract class Pratura : Application() {
  abstract val basketItemSaver: BasketItemSaver
  abstract val basketItemsRetriever: BasketItemsRetriever
  abstract val productSaver: ProductSaver
  abstract val productRetriever: ProductRetriever
  abstract val currencySetter: CurrencySetter
  abstract val currencyRetriever: CurrencyRetriever
  abstract val shippingAddressRetriever: ShippingAddressRetriever

  override fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>? = null
  override fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>? {
    return when (useCase) {
      AddItemToBasket::class -> uk.co.craigbass.pratura.usecase.basket.AddItemToBasket(
        basketItemSaver,
        basketItemsRetriever
      )
      ViewBasket::class -> uk.co.craigbass.pratura.usecase.basket.ViewBasket(
        basketItemsRetriever,
        productRetriever,
        currencyRetriever
      )
      AddProduct::class -> uk.co.craigbass.pratura.usecase.administration.AddProduct(productSaver)
      ViewAllProducts::class -> uk.co.craigbass.pratura.usecase.catalogue.ViewAllProducts(
        productRetriever,
        currencyRetriever
      )
      AddShippingAddress::class -> uk.co.craigbass.pratura.usecase.checkout.AddShippingAddress()
      ViewDraftOrderStatus::class -> uk.co.craigbass.pratura.usecase.checkout.ViewDraftOrderStatus(
        basketItemsRetriever,
        shippingAddressRetriever
      )
      SetStoreCurrency::class -> uk.co.craigbass.pratura.usecase.administration.SetStoreCurrency(currencySetter)
      else -> throw Exception("Use Case Not Found")
    }
  }
}
