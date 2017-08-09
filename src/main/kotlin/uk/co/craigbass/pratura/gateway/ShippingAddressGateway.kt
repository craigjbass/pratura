package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.checkout.*

interface ShippingAddressGateway : ShippingAddressSaver, ShippingAddressRetriever
