package uk.co.craigbass.pratura.api

import com.madetech.clean.boundary.SynchronousUseCaseExecutor
import com.madetech.clean.usecase.SynchronousUseCase
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class SpyUseCaseExecutor(private val response: Any) : SynchronousUseCaseExecutor {
  val requestsReceived: MutableList<Any> = mutableListOf()

  override fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES {
    requestsReceived.add(request as Any)
    return response as RES
  }
}
