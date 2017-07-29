package uk.co.craigbass.pratura.boundary.ViewBasket

data class PresentableBasket(val lineItems: List<PresentableLineItem>, val basketValue: String)
data class PresentableLineItem(val quantity: Int, val sku: String)