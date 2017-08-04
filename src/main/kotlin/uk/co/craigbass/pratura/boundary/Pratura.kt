package uk.co.craigbass.pratura.boundary

import com.madetech.clean.Application
import com.madetech.clean.usecase.*
import uk.co.craigbass.pratura.boundary.usecase.AddItemToBasket
import uk.co.craigbass.pratura.boundary.usecase.AddProduct
import uk.co.craigbass.pratura.boundary.usecase.SetStoreCurrency
import uk.co.craigbass.pratura.boundary.usecase.ViewAllProducts
import uk.co.craigbass.pratura.boundary.usecase.ViewBasket
import uk.co.craigbass.pratura.usecase.*
import uk.co.craigbass.pratura.usecase.CurrencySetter
import kotlin.reflect.KClass

abstract class Pratura : Application() {
  abstract val basketItemSaver: BasketItemSaver
  abstract val basketItemsRetriever: BasketItemsRetriever
  abstract val productSaver: ProductSaver
  abstract val productRetriever: ProductRetriever
  abstract val currencySetter: CurrencySetter
  abstract val currencyRetriever: CurrencyRetriever

  override fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>? = null
  override fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>? {
    return when (useCase) {
      AddItemToBasket::class -> uk.co.craigbass.pratura.usecase.AddItemToBasket(
        basketItemSaver,
        basketItemsRetriever
      )
      ViewBasket::class -> uk.co.craigbass.pratura.usecase.ViewBasket(
        basketItemsRetriever,
        productRetriever,
        currencyRetriever
      )
      AddProduct::class -> uk.co.craigbass.pratura.usecase.AddProduct(productSaver)
      ViewAllProducts::class -> uk.co.craigbass.pratura.usecase.ViewAllProducts(
        productRetriever,
        currencyRetriever
      )
      SetStoreCurrency::class -> uk.co.craigbass.pratura.usecase.SetStoreCurrency(currencySetter)
      else -> throw Exception("Use Case Not Found")
    }
  }
}
