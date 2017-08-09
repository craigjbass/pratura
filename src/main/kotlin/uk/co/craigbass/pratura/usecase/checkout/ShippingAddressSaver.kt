package uk.co.craigbass.pratura.usecase.checkout

import uk.co.craigbass.pratura.domain.ShippingAddress

interface ShippingAddressSaver {
  fun save(shippingAddress: ShippingAddress)
}
