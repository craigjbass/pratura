package uk.co.craigbass.pratura.boundary

import com.madetech.clean.Application
import com.madetech.clean.usecase.*
import uk.co.craigbass.pratura.usecase.*
import kotlin.reflect.KClass

abstract class Pratura : Application() {
  abstract val basketItemSaver: BasketItemSaver
  abstract val basketItemsRetriever: BasketItemsRetriever

  override fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>? = null
  override fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>? {
    return when (useCase) {
      AddItemToBasket::class -> uk.co.craigbass.pratura.usecase.AddItemToBasket(
        basketItemSaver,
        basketItemsRetriever
      )
      ViewBasket::class -> uk.co.craigbass.pratura.usecase.ViewBasket(
        basketItemsRetriever
      )
      else -> throw Exception("Use Case Not Found")
    }
  }
}