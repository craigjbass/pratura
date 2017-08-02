package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.math.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever,
                 private val productRetriever: ProductRetriever) : ViewBasket {
  override fun execute(request: Unit): PresentableBasket {
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
      unitPrice = pricedLineItem.presentableUnitPrice,
      total = pricedLineItem.presentableTotal
    )
  }

  private fun getBasketTotal(pricedLineItems: List<PricedLineItem>): String {
    return pricedLineItems
      .map(PricedLineItem::total)
      .getSumOfTotals()
      .toCurrencyWithSymbol()
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

    val presentableUnitPrice = unitPrice.toCurrencyWithSymbol()
    val presentableTotal = total.toCurrencyWithSymbol()
  }
}
