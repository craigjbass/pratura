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

  private fun ShippingAddress?.toPresentableAddress() = this?.let {
    ViewDraftOrder.PresentableAddress(
      name = name,
      companyName = companyName,
      addressLine1 = addressLine1,
      addressLine2 = addressLine2 ?: "",
      addressLine3 = addressLine3 ?: "",
      city = city,
      province = province,
      zipcode = zipcode
    )
  }
}

