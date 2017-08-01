package uk.co.craigbass.pratura.domain

import uk.co.craigbass.pratura.math.toDecimal

typealias PricedLineItem = Pair<BasketItem, Product?>

fun PricedLineItem.getItemPrice() = this.second?.price ?: "0.00".toDecimal()
fun PricedLineItem.getQuantity() = this.first.quantity.toDecimal()
fun createPricedLineItem(item: BasketItem, product: Product?) = Pair(item, product)
