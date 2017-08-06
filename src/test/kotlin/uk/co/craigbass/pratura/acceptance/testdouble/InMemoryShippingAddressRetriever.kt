package uk.co.craigbass.pratura.acceptance.testdouble

import uk.co.craigbass.pratura.usecase.checkout.ShippingAddressRetriever

class InMemoryShippingAddressRetriever : ShippingAddressRetriever {
  override fun getShippingAddress() = null
}
