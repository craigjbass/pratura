package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.domain.Product

interface ProductRetriever {
  fun all(): List<Product>
}
