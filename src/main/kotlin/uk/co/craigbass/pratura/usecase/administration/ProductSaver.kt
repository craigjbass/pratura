package uk.co.craigbass.pratura.usecase.administration

import uk.co.craigbass.pratura.domain.Product

interface ProductSaver {
  fun save(product: Product)
}
