package uk.co.craigbass.pratura.unit.testdouble

class SpyBasketReader(basketItems: Baskets) : StubBasketReader(basketItems) {
  var basketIdsSentToBasketExists = mutableListOf<String>()

  override fun `basketExists?`(basketId: String): Boolean {
    basketIdsSentToBasketExists.add(basketId)
    return super.`basketExists?`(basketId)
  }
}
