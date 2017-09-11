package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder
import uk.co.craigbass.pratura.boundary.checkout.ViewDraftOrder.*
import uk.co.craigbass.pratura.domain.ShippingAddress
import uk.co.craigbass.pratura.usecase.BasketReader

class ViewDraftOrder(private val basketReader: BasketReader,
                     private val shippingAddressRetriever: ShippingAddressRetriever) : ViewDraftOrder {
  var shippingAddress: ShippingAddress? = null

  override fun execute(request: Request): ViewDraftOrder.Response {
    if (basketNotFound(request)) return basketNotFound()
    shippingAddress = shippingAddressRetriever.getShippingAddress()

    return ViewDraftOrder.Response(
      `readyToComplete?` = `shippingAddress?`() && hasBasketItems(request.basketId),
      shippingAddress = shippingAddress.toPresentableAddress(),
      errors = setOf()
    )
  }

  private fun basketNotFound(request: Request) = !basketReader.`basketExists?`(request.basketId)

  private fun basketNotFound() = Response(
    `readyToComplete?` = false,
    shippingAddress = null,
    errors = setOf("BASKET_NOT_FOUND")
  )

  private fun hasBasketItems(basketId: String) = basketReader.getAll(basketId).count() > 0

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

