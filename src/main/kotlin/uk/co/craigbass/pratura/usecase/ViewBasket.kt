package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.domain.Currency
import uk.co.craigbass.pratura.math.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever,
                 private val productRetriever: ProductRetriever,
                 private val currencyRetriever: CurrencyRetriever) : ViewBasket {
  lateinit var currency: Currency

  override fun execute(request: Unit): PresentableBasket {
    currency = currencyRetriever.getCurrencyCurrency()
    val pricedLineItems = pricedLineItems()
    return PresentableBasket(
      lineItems = pricedLineItems.map(this::toPresentableLineItem),
      basketValue = getBasketTotal(pricedLineItems)
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

  private fun pricedLineItems(): List<PricedLineItem> {
    val products = productRetriever.all()
    return getBasketItems().map { item ->
      val product = products.find { p -> item.sku == p.sku }
      PricedLineItem(item, product!!)
    }
  }

  private fun getBasketItems() = basketItemsGateway.all()

  private fun List<BigDecimal>.getSumOfTotals() = fold(ZERO) { a, b -> a + b }

  class PricedLineItem(item: BasketItem, product: Product) {
    val quantity = item.quantity
    val name = product.name
    val sku = item.sku
    val unitPrice = product.price
    val total = unitPrice * quantity.toDecimal()
  }
}
