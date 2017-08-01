package uk.co.craigbass.pratura.main

import uk.co.craigbass.pratura.boundary.Pratura
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.gateway.BasketItemsGateway
import uk.co.craigbass.pratura.http.*
import uk.co.craigbass.pratura.usecase.*

fun main(args: Array<String>) {
  val webServer = WebServer()
  webServer.addController(AddToBasketController(StandInPratura()))
  webServer.start()
}

class StandInPratura : Pratura(), BasketItemsGateway {
  override fun all(): List<BasketItem> = listOf()
  override fun save(item: BasketItem) = Unit

  override val basketItemSaver: BasketItemSaver
    get() = this
  override val basketItemsRetriever: BasketItemsRetriever
    get() = this
}