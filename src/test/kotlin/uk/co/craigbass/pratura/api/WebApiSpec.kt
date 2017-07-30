package uk.co.craigbass.pratura.api

import com.github.kittinunf.fuel.httpPost
import com.github.salomonbrys.kotson.jsonObject
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import uk.co.craigbass.pratura.http.AddToBasketController
import uk.co.craigbass.pratura.http.WebServer
import uk.co.craigbass.pratura.testdouble.SpyAddToBasket

class WebApiSpec : Spek({
    val webServer = memoized { WebServer() }

    fun performAddItemToBasket(quantity: Int, sku: String) {
        webServer().start()

        val (_, response, _) = "http://localhost:8080/AddItemToBasket"
                .httpPost()
                .body(jsonObject(
                        "quantity" to quantity,
                        "sku" to sku
                ).toString())
                .responseString()

        response.httpStatusCode.shouldEqual(200)

        webServer().stop()
    }

    describe("add to basket controller") {
        val spyUseCase = memoized { SpyAddToBasket() }

        beforeEachTest {
            webServer().addController(AddToBasketController(spyUseCase()))
        }

        val subject = memoized { spyUseCase().requestsReceived }

        given("a web request is made to add item to basket") {
            beforeEachTest {
                performAddItemToBasket(
                        quantity = 1,
                        sku = "sku:54382"
                )
            }

            it("only one call is made to the use case") {
                subject().count().shouldEqual(1)
            }

            it("quantity is sent correctly") {
                subject().first().quantity.shouldEqual(1)
            }

            it("sku is sent correctly") {
                subject().first().sku.shouldEqual("sku:54382")
            }
        }

        given("5 of (sku:1001) are added to the basket") {
            beforeEachTest {
                performAddItemToBasket(
                        quantity = 5,
                        sku = "sku:1001"
                )
            }

            it("quantity is sent correctly") {
                subject().first().quantity.shouldEqual(5)
            }

            it("sku is sent correctly") {
                subject().first().sku.shouldEqual("sku:1001")
            }
        }
    }
})


