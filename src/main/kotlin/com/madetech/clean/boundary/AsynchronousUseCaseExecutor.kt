package com.madetech.clean.boundary

import com.madetech.clean.usecase.AsynchronousUseCase
import kotlin.reflect.KClass

interface AsynchronousUseCaseExecutor {
  fun <U : AsynchronousUseCase<R, P>, R, P> executeUseCase(useCase: KClass<U>, request: R, presenter: P)
}