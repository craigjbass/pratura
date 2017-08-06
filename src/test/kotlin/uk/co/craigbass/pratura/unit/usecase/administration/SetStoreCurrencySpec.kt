package uk.co.craigbass.pratura.unit.usecase.administration

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import uk.co.craigbass.pratura.boundary.administration.SetStoreCurrency.Request
import uk.co.craigbass.pratura.domain.Currency
import uk.co.craigbass.pratura.usecase.administration.*

class SetStoreCurrencySpec : Spek({
  class SpyCurrencySetter : CurrencySetter {
    lateinit var currentCurrency: Currency

    override fun set(currency: Currency) {
      this.currentCurrency = currency
    }
  }

  val spyCurrencySetter = memoized { SpyCurrencySetter() }

  given("store currency is set to GBP and country to GB") {
    beforeEachTest {
      SetStoreCurrency(spyCurrencySetter())
        .execute(Request(
          currency = "GBP",
          country = "GB",
          language = "en"
        ))
    }

    it("sets the currency currency to GBP") {
      spyCurrencySetter().currentCurrency.currency.shouldEqual("GBP")
    }

    it("sets the currency country to GB") {
      spyCurrencySetter().currentCurrency.country.shouldEqual("GB")
    }

    it("sets the currency language to en") {
      spyCurrencySetter().currentCurrency.language.shouldEqual("en")
    }
  }

  given("store currency is set to EUR and country to NL") {
    beforeEachTest {
      SetStoreCurrency(spyCurrencySetter())
        .execute(Request(
          currency = "EUR",
          country = "NL",
          language = "nl"
        ))
    }

    it("sets the currency currency to EUR") {
      spyCurrencySetter().currentCurrency.currency.shouldEqual("EUR")
    }

    it("sets the currency country to NL") {
      spyCurrencySetter().currentCurrency.country.shouldEqual("NL")
    }

    it("sets the currency language to nl") {
      spyCurrencySetter().currentCurrency.language.shouldEqual("nl")
    }
  }
})
