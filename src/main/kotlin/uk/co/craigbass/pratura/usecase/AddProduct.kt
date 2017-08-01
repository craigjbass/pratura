package uk.co.craigbass.pratura.usecase

import uk.co.craigbass.pratura.boundary.AddProduct
import uk.co.craigbass.pratura.boundary.AddProduct.Request
import uk.co.craigbass.pratura.domain.Product

class AddProduct(val productSaver: ProductSaver) : AddProduct {
  override fun execute(request: Request) {
    productSaver.save(Product(request.sku, request.price))
  }

}

