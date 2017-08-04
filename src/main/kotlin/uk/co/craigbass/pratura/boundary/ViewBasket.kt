package uk.co.craigbass.pratura.boundary

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.ViewBasket.PresentableBasket

interface ViewBasket : SynchronousUseCase<Unit, PresentableBasket> {
  data class PresentableBasket(val lineItems: List<PresentableLineItem>, val basketValue: String)

  data class PresentableLineItem(
    val quantity: Int,
    val sku: String,
    val name: String,
    val unitPrice: String,
    val total: String
  )
}
