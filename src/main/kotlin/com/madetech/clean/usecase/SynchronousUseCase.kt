package com.madetech.clean.usecase

interface SynchronousUseCase<in REQUEST, out RESPONSE> {
  fun execute(request: REQUEST): RESPONSE
}

fun <RESPONSE> SynchronousUseCase<Unit, RESPONSE>.execute() = execute(Unit)
