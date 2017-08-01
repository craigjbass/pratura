package uk.co.craigbass.pratura.http

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.madetech.clean.boundary.SynchronousUseCaseExecutor
import uk.co.craigbass.pratura.boundary.AddItemToBasket

class AddToBasketController(private val useCaseExecutor: SynchronousUseCaseExecutor) : WebServer.Controller {
  override val path: String = "/AddItemToBasket"

  override fun execute(requestBody: String): String {
    useCaseExecutor.executeUseCase(
      AddItemToBasket::class,
      Gson().fromJson<AddItemToBasket.Request>(requestBody)
    )
    return "{}"
  }
}
