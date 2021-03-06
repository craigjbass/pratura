package com.madetech.clean

import com.madetech.clean.UseCaseExecutor.UnsafeConstructor
import com.madetech.clean.boundary.*
import com.madetech.clean.usecase.*
import kotlin.reflect.KClass

abstract class Application :
  UnsafeConstructor,
  AsynchronousUseCaseExecutor,
  SynchronousUseCaseExecutor {
  private val useCaseExecutor: UseCaseExecutor
    get() = UseCaseExecutor(this)

  override fun <U : AsynchronousUseCase<R, P>, R, P> executeUseCase(useCase: KClass<U>, request: R, presenter: P) {
    useCaseExecutor.executeUseCase(useCase, request, presenter)
  }

  override fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES {
    return useCaseExecutor.executeUseCase(useCase, request)
  }
}
