package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.domain.ShippingAddress

interface ShippingAddressRetriever {
  fun getShippingAddress(): ShippingAddress?
}
