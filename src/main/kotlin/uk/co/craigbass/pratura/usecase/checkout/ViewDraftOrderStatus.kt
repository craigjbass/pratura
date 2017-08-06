package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrderStatus
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrderStatus.Response
import uk.co.craigbass.pratura.usecase.BasketItemsRetriever

class ViewDraftOrderStatus(private val basketItemsRetriever: BasketItemsRetriever,
                           private val shippingAddressRetriever: ShippingAddressRetriever) : ViewDraftOrderStatus {
  override fun execute(request: Unit): Response {
    return Response(
      isReady = hasShippingAddress() && hasBasketItems()
    )
  }

  private fun hasBasketItems() = basketItemsRetriever.getAll().count() > 0

  private fun hasShippingAddress() = shippingAddressRetriever.getShippingAddress() != null
}

