package uk.co.craigbass.pratura.http

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
            call.request
              .receiveContent()
              .inputStream()
              .bufferedReader()
              .use {
              call.respondText(controller.execute(it.readText()))
            }
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
