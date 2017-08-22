package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder
import uk.co.craigbass.pratura.domain.ShippingAddress
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

class ViewDraftOrder(private val basketItemsRetriever: BasketItemsRetriever,
                     private val shippingAddressRetriever: ShippingAddressRetriever) : ViewDraftOrder {
  var shippingAddress: ShippingAddress? = null

  override fun execute(request: Unit): ViewDraftOrder.Response {
    shippingAddress = shippingAddressRetriever.getShippingAddress()

    return ViewDraftOrder.Response(
      `isReadyToComplete?` = `shippingAddress?`() && hasBasketItems(),
      shippingAddress = shippingAddress.toPresentableAddress()
    )
  }

  private fun hasBasketItems() = basketItemsRetriever.getAll().count() > 0

  private fun `shippingAddress?`() = shippingAddress != null

  private fun ShippingAddress?.toPresentableAddress() = this?.let { address ->
    ViewDraftOrder.PresentableAddress(
      name = address.name,
      companyName = address.companyName
    )
  }
}

