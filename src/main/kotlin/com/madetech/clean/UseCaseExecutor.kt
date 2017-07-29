package com.madetech.clean

import com.madetech.clean.boundary.AsynchronousUseCaseExecutor
import com.madetech.clean.boundary.SynchronousUseCaseExecutor
import com.madetech.clean.usecase.AsynchronousUseCase
import com.madetech.clean.usecase.SynchronousUseCase
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class UseCaseExecutor(val unsafeConstructor: UnsafeConstructor) :
        AsynchronousUseCaseExecutor,
        SynchronousUseCaseExecutor {

    override fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES
            = (synchronousUseCase(useCase) as U).execute(request)

    override fun <U : AsynchronousUseCase<R, P>, R, P> executeUseCase(useCase: KClass<U>, request: R, presenter: P)
            = (asynchronousUseCase(useCase) as U).execute(request, presenter)

    private fun <REQ, RES, U : SynchronousUseCase<REQ, RES>> synchronousUseCase(useCase: KClass<U>): SynchronousUseCase<*, *>?
            = unsafeConstructor.unsafeConstructSynchronous(useCase)

    private fun <P, R, U : AsynchronousUseCase<R, P>> asynchronousUseCase(useCase: KClass<U>): AsynchronousUseCase<*, *>?
            = unsafeConstructor.unsafeConstructAsynchronous(useCase)

    interface UnsafeConstructor {
        fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>?
        fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>?
    }
}
