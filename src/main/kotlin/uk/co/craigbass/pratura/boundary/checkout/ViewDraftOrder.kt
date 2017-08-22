package uk.co.craigbass.pratura.boundary.checkout

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.Response

interface ViewDraftOrder : SynchronousUseCase<Unit, Response> {
  data class PresentableAddress(
    val name: String,
    val companyName: String?
  )

  data class Response(
    val `isReadyToComplete?`: Boolean,
    val shippingAddress: PresentableAddress?
  )
}
