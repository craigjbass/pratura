package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.Product

interface ProductSaver {
  fun save(product: Product)
}