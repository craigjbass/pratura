package uk.co.craigbass.pratura.boundary.checkout

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress.Request

interface AddShippingAddress : SynchronousUseCase<Request, Unit> {
  data class Request(
    val name: String?,
    val companyName: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val addressLine3: String?,
    val city: String?,
    val province: String?,
    val zipcode: String?
  )
}
