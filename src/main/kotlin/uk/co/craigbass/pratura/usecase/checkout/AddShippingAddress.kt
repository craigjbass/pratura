package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress
import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress.Request
import uk.co.craigbass.pratura.domain.ShippingAddress

class AddShippingAddress(private val shippingAddressSaver: ShippingAddressSaver) : AddShippingAddress {
  override fun execute(request: Request) {
    shippingAddressSaver.save(
      ShippingAddress(
        name = request.name,
        companyName = request.companyName,
        addressLine1 = request.addressLine1,
        addressLine2 = request.addressLine2,
        addressLine3 = request.addressLine3,
        city = request.city,
        province = request.province,
        zipcode = request.zipcode
      )
    )
  }
}
