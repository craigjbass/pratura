package uk.co.craigbass.pratura.http

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import uk.co.craigbass.pratura.boundary.AddItemToBasket

class AddToBasketController(private val useCase: AddItemToBasket) : WebServer.Controller {
    override val path: String = "/AddItemToBasket"

    override fun execute(requestBody: String): String {
        useCase.execute(Gson().fromJson<AddItemToBasket.Request>(requestBody))
        return "{}"
    }
}
