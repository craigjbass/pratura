package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress
import uk.co.craigbass.pratura.boundary.checkout.AddShippingAddress.Request
import uk.co.craigbass.pratura.domain.ShippingAddress

class AddShippingAddress(private val shippingAddressSaver: ShippingAddressSaver) : AddShippingAddress {
  override fun execute(request: Request) {
    shippingAddressSaver.save(ShippingAddress(name = request.name))
  }
}
