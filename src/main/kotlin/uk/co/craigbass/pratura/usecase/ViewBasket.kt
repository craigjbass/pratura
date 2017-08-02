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
      lineItems = getLineItems(pricedLineItems),
      basketValue = getBasketTotal(pricedLineItems)
    )
  }

  private fun getLineItems(pricedLineItems: List<PricedLineItem>): List<PresentableLineItem> {
    return pricedLineItems.map { pricedLineItem ->
      PresentableLineItem(
        quantity = pricedLineItem.getQuantity().toInt(),
        sku = pricedLineItem.getSku(),
        name = pricedLineItem.getName(),
        unitPrice = pricedLineItem.getUnitPriceAsCurrency(),
        total = pricedLineItem.getTotalAsCurrency()
      )
    }
  }

  private fun getBasketTotal(pricedLineItems: List<PricedLineItem>): String {
    return pricedLineItems
      .map(PricedLineItem::getTotal)
      .getSumOfTotals()
      .toCurrencyWithSymbol()
  }

  private fun pricedLineItems(): List<PricedLineItem> {
    val products = productRetriever.all()
    return getBasketItems().map { item ->
      val product = products.find { p -> item.sku == p.sku }
      PricedLineItem(item, product)
    }
  }

  private fun getBasketItems() = basketItemsGateway.all()

  private fun List<BigDecimal>.getSumOfTotals() = fold(ZERO) { a, b -> a + b }

  class PricedLineItem(val item: BasketItem, val product: Product?) {
    fun getQuantity() = item.quantity.toDecimal()
    fun getName() = product?.name ?: ""
    fun getSku() = item.sku

    fun getUnitPrice() = product?.price ?: ZERO
    fun getTotal() = getUnitPrice() * getQuantity()

    fun getUnitPriceAsCurrency() = getUnitPrice().toCurrencyWithSymbol()
    fun getTotalAsCurrency() = getTotal().toCurrencyWithSymbol()
  }
}
