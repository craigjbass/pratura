package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.ViewBasket
import uk.co.craigbass.pratura.boundary.ViewBasket.*
import uk.co.craigbass.pratura.domain.*
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class ViewBasket(private val basketItemsGateway: BasketItemsRetriever,
                 private val productRetriever: ProductRetriever) : ViewBasket {
  private var items: List<BasketItem>? = null
  private var products: List<Product>? = null

  override fun execute(request: Unit): PresentableBasket {
    items = basketItemsGateway.all()
    products = productRetriever.all()

    return PresentableBasket(lineItems(), "£${getBasketValue()}")
  }

  private fun lineItems(): List<PresentableLineItem> {
    return items!!.map { item ->
      PresentableLineItem(
        quantity = item.quantity,
        sku = item.sku,
        name = getProductFor(item)?.name ?: ""
      )
    }
  }

  private fun getBasketValue(): String? {
    return items!!
      .map(this::toPricedLineItem)
      .map(this::toLineItemTotal)
      .getSumOfTotals()
      .toCurrencyString()
  }

  private fun toLineItemTotal(lineItem: PricedLineItem) = lineItem.getQuantity() * lineItem.getItemPrice()

  private fun toPricedLineItem(item: BasketItem) = createPricedLineItem(item, getProductFor(item))

  private fun getProductFor(item: BasketItem) = products?.find { p -> item.sku == p.sku }

  private fun List<BigDecimal>.getSumOfTotals() = fold(ZERO) { a, b -> a + b }

  private fun BigDecimal.toCurrencyString() = this.setScale(2).toPlainString()
}
