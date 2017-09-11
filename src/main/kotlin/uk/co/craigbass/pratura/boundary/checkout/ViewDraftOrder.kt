package uk.co.craigbass.pratura.boundary.checkout

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.*

interface ViewDraftOrder : SynchronousUseCase<Request, Response> {
  data class Request(
    val basketId: String
  )

  data class Response(
    val `readyToComplete?`: Boolean,
    val shippingAddress: PresentableAddress?,
    val errors: Set<String>
  )

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
}
