package uk.co.craigbass.pratura.unit.usecase.basket

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.basket.AddItemToBasket.Request
import uk.co.craigbass.pratura.domain.BasketItem
import uk.co.craigbass.pratura.unit.testdouble.StubBasketReader
import uk.co.craigbass.pratura.unit.usecase.basket.testdouble.SpyBasketItemsWriter
import uk.co.craigbass.pratura.usecase.basket.AddItemToBasket

class AddItemToBasketSpec : Spek({
  var baskets: Map<String, List<BasketItem>>? = null
  val basketWriter = memoized { SpyBasketItemsWriter() }
  val basketReader = memoized {
    StubBasketReader(baskets!!)
  }
  val addItemToBasket = memoized {
    AddItemToBasket(
      basketWriter(),
      basketReader()
    )
  }

  given("basket does not exist") {
    val response = memoized {
      baskets = mapOf()
      addItemToBasket().execute(Request(1, "sku", "not-found-basket"))
    }

    it("should fail with a basket not found error") {
      response().errors.shouldContain("BASKET_NOT_FOUND")
    }
  }

  given("basket contains nothing and one item is added to basket") {
    beforeEachTest {
      baskets = mapOf(Pair("an-id-that-exists", listOf()))
      addItemToBasket().execute(Request(1, "sku", "an-id-that-exists"))
    }

    it("saves a new item to the basket") {
      basketWriter().lastSavedItem("an-id-that-exists")!!.quantity.shouldBe(1)
      basketWriter().lastSavedItem("an-id-that-exists")!!.sku.shouldBe("sku")
    }
  }

  given("basket contains nothing and two of one item is added to basket") {
    beforeEachTest {
      baskets = mapOf(Pair("an-id-that-exists", listOf()))
      addItemToBasket().execute(Request(2, "sku", "an-id-that-exists"))
    }

    it("saves a new item to the basket") {
      basketWriter().lastSavedItem("an-id-that-exists")!!.quantity.shouldBe(2)
      basketWriter().lastSavedItem("an-id-that-exists")!!.sku.shouldBe("sku")
    }
  }

  given("one item is already in the basket") {
    beforeEachTest {
      baskets = mapOf(
        Pair(
          "an-id-that-exists",
          listOf(
            BasketItem(1, "sku")
          )
        )
      )
      addItemToBasket().execute(Request(1, "sku", "an-id-that-exists"))
    }

    it("should update basket item with new quantity") {
      basketWriter().lastSavedItem("an-id-that-exists")?.quantity.shouldBe(2)
    }
  }

  given("one item is already in a basket, and we add to a different basket") {
    beforeEachTest {
      baskets = mapOf(
        Pair(
          "an-id-that-exists",
          listOf(
            BasketItem(1, "sku")
          )
        ),
        Pair(
          "another-basket-id",
          listOf()
        )
      )
      addItemToBasket().execute(Request(1, "sku", "another-basket-id"))
    }

    it("did not change the basket not added to") {
      basketWriter().lastSavedItem("an-id-that-exists").shouldBeNull()
    }
  }
})
