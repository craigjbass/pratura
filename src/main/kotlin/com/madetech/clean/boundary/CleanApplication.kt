package com.madetech.clean.boundary

import com.madetech.clean.boundary.UseCaseExecutor.UnsafeConstructor
import com.madetech.clean.usecase.AsynchronousUseCase
import com.madetech.clean.usecase.SynchronousUseCase
import kotlin.reflect.KClass

abstract class CleanApplication : UnsafeConstructor {
    val useCaseExecutor: UseCaseExecutor
        get() = UseCaseExecutor(this)

    fun <U : AsynchronousUseCase<R, P>, R, P> executeUseCase(useCase: KClass<U>, request: R, presenter: P) {
        useCaseExecutor.executeUseCase(useCase, request, presenter)
    }

    fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES {
        return useCaseExecutor.executeUseCase(useCase, request)
    }
}