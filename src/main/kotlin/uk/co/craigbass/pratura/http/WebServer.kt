package uk.co.craigbass.pratura.http

import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.host.*
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.*
import java.util.concurrent.TimeUnit

class WebServer {
  private val controllers: MutableList<Controller> = mutableListOf()
  private val server: ApplicationHost = embeddedServer(Netty, 8080) {
    routing {
      controllers.forEach { controller ->
        post(controller.path) {
          val requestBody = call.request.receive(String::class)
          call.respondText(controller.execute(requestBody))
        }
      }
    }
  }

  fun start() {
    server.start()
  }

  fun stop() {
    server.stop(0, 0, TimeUnit.SECONDS)
  }

  fun addController(controller: Controller) {
    controllers.add(controller)
  }

  interface Controller {
    val path: String
    fun execute(requestBody: String): String
  }
}
