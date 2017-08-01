package com.madetech.clean.usecase

interface AsynchronousUseCase<in REQUEST, in PRESENTER> {
  fun execute(request: REQUEST, presenter: PRESENTER)
}