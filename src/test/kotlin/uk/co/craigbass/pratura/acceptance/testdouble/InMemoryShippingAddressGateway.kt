package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.domain.ShippingAddress
import uk.co.craigbass.pratura.gateway.ShippingAddressGateway

class InMemoryShippingAddressGateway : ShippingAddressGateway {
  private var _shippingAddress: ShippingAddress? = null

  override fun save(shippingAddress: ShippingAddress) {
    _shippingAddress = shippingAddress
  }

  override fun getShippingAddress() = _shippingAddress
}
