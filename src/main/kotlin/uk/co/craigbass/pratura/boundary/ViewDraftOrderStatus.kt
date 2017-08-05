package uk.co.craigbass.pratura.boundary

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.ViewDraftOrderStatus.*

interface ViewDraftOrderStatus : SynchronousUseCase<Unit, Response> {
  data class Response(val orderCanBePlaced: Boolean)
}
