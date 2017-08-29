package uk.co.craigbass.pratura.boundary.checkout

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.Response

interface ViewDraftOrder : SynchronousUseCase<Unit, Response> {
  data class PresentableAddress(
    val name: String,
    val companyName: String?,
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String,
    val city: String,
    val province: String,
    val zipcode: String
  )

  data class Response(
    val `isReadyToComplete?`: Boolean,
    val shippingAddress: PresentableAddress?
  )
}
