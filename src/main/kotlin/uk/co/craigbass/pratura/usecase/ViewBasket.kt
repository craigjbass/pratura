package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*
import uk.co.craigbass.pratura.domain.*
import uk.co.craigbass.pratura.math.toDecimal
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

typealias PricedLineItem = Pair<BasketItem, Product?>

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever,
                 private val productRetriever: ProductRetriever) : ViewBasket {
  private var items: List<BasketItem>? = null
  private var products: List<Product>? = null

  override fun execute(request: Unit): PresentableBasket {
    items = basketItemsGateway.all()
    products = productRetriever.all()

    return PresentableBasket(lineItems(), "£${getBasketValue()}")
  }

  private fun lineItems() = items!!.map { item -> PresentableLineItem(item.quantity, item.sku) }

  private fun getBasketValue(): String? {
    return items!!
      .map(this::toPricedLineItem)
      .map(this::toLineItemTotal)
      .getSumOfTotals()
      .toCurrencyString()
  }

  private fun toLineItemTotal(lineItem: PricedLineItem) = lineItem.getQuantity() * lineItem.getItemPrice()

  private fun toPricedLineItem(item: BasketItem) = Pair(item, getProductFor(item))

  private fun getProductFor(item: BasketItem) = products?.find { p -> item.sku == p.sku }

  private fun List<BigDecimal>.getSumOfTotals() = fold(ZERO) { a, b -> a + b }

  private fun PricedLineItem.getItemPrice() = this.second?.price ?: "0.00".toDecimal()

  private fun PricedLineItem.getQuantity() = this.first.quantity.toDecimal()

  private fun BigDecimal.toCurrencyString() = this.setScale(2).toPlainString()
}
