package uk.co.craigbass.pratura.boundary

import com.madetech.clean.usecase.SynchronousUseCase

interface ViewBasket : SynchronousUseCase<Unit, ViewBasket.PresentableBasket> {
  data class PresentableBasket(val lineItems: List<PresentableLineItem>, val basketValue: String)
  data class PresentableLineItem(val quantity: Int, val sku: String, val name: String)
}