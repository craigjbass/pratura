package uk.co.craigbass.pratura.boundary.basket

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.basket.ViewBasket.*

interface ViewBasket : SynchronousUseCase<Request, PresentableBasket> {
  data class Request(val basketId: String)

  data class PresentableBasket(
    val lineItems: List<PresentableLineItem>,
    val basketValue: String,
    val errors: Set<String>
  )

  data class PresentableLineItem(
    val quantity: Int,
    val sku: String,
    val name: String,
    val unitPrice: String,
    val total: String
  )
}
