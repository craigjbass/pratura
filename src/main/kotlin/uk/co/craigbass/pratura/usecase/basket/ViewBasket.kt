package uk.co.craigbass.pratura.usecase.basket

import uk.co.craigbass.pratura.boundary.basket.ViewBasket
import uk.co.craigbass.pratura.boundary.basket.ViewBasket.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.math.toDecimal
import uk.co.craigbass.pratura.usecase.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ViewBasket(private val basketItemsGateway: BasketReader,
                 private val productRetriever: ProductRetriever,
                 private val currencyRetriever: CurrencyRetriever) : ViewBasket {
  lateinit var currency: Currency

  override fun execute(request: Request): PresentableBasket {
    if (!basketItemsGateway.`basketExists?`(request.basketId)) {
      return PresentableBasket(
        lineItems = listOf(),
        basketValue = "",
        errors = setOf("BASKET_NOT_FOUND")
      )
    }


    currency = currencyRetriever.getCurrentCurrency()
    val pricedLineItems = pricedLineItems(request.basketId)
    return PresentableBasket(
      lineItems = pricedLineItems.map(this::toPresentableLineItem),
      basketValue = getBasketTotal(pricedLineItems),
      errors = setOf()
    )
  }

  private fun toPresentableLineItem(pricedLineItem: PricedLineItem): PresentableLineItem {
    return PresentableLineItem(
      quantity = pricedLineItem.quantity,
      sku = pricedLineItem.sku,
      name = pricedLineItem.name,
      unitPrice = currency.format(pricedLineItem.unitPrice),
      total = currency.format(pricedLineItem.total)
    )
  }

  private fun getBasketTotal(pricedLineItems: List<PricedLineItem>): String {
    val basketSum = pricedLineItems
      .map(PricedLineItem::total)
      .getSumOfTotals()
    return currency.format(basketSum)
  }

  private fun pricedLineItems(basketId: String): List<PricedLineItem> {
    val products = productRetriever.all()
    return getBasketItems(basketId).map { item ->
      val product = products.find { p -> item.sku == p.sku }
      PricedLineItem(item, product!!)
    }
  }

  private fun getBasketItems(basketId: String) = basketItemsGateway.getAll(basketId)

  private fun List<BigDecimal>.getSumOfTotals() = fold(ZERO) { a, b -> a + b }

  class PricedLineItem(item: BasketItem, product: Product) {
    val quantity = item.quantity
    val name = product.name
    val sku = item.sku
    val unitPrice = product.price
    val total = unitPrice * quantity.toDecimal()
  }
}
