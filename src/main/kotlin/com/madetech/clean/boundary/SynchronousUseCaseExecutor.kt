package com.madetech.clean.boundary

import com.madetech.clean.usecase.SynchronousUseCase
import kotlin.reflect.KClass

interface SynchronousUseCaseExecutor {
    fun <U : SynchronousUseCase<REQ, RES>, REQ, RES> executeUseCase(useCase: KClass<U>, request: REQ): RES
}