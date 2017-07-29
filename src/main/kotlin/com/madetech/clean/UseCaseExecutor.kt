package com.madetech.clean

import com.madetech.clean.boundary.AsynchronousUseCaseExecutor
import com.madetech.clean.boundary.SynchronousUseCaseExecutor
import com.madetech.clean.usecase.AsynchronousUseCase
import com.madetech.clean.usecase.SynchronousUseCase
import kotlin.reflect.KClass

class UseCaseExecutor(val unsafeConstructor: UnsafeConstructor) :
        AsynchronousUseCaseExecutor,
        SynchronousUseCaseExecutor {

    override fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES {
        val useCaseInstance = unsafeConstructor.unsafeConstructSynchronous(useCase)
        if (useCaseInstance == null) throw Exception("Use Case Not Found")
        return (useCaseInstance as U).execute(request)
    }

    override fun <U : AsynchronousUseCase<R, P>, R, P> executeUseCase(useCase: KClass<U>, request: R, presenter: P) {
        val useCaseInstance = unsafeConstructor.unsafeConstructAsynchronous(useCase)
        if (useCaseInstance == null) throw Exception("Use Case Not Found")
        (useCaseInstance as U).execute(request, presenter)
    }

    interface UnsafeConstructor {
        fun unsafeConstructAsynchronous(useCase: KClass<*>): AsynchronousUseCase<*, *>?
        fun unsafeConstructSynchronous(useCase: KClass<*>): SynchronousUseCase<*, *>?
    }
}