package uk.co.craigbass.pratura.boundary.checkout

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrderStatus.Response

interface ViewDraftOrderStatus : SynchronousUseCase<Unit, Response> {
  data class Response(val isReady: Boolean)
}
