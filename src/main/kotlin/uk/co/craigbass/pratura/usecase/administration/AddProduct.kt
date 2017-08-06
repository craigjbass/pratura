package uk.co.craigbass.pratura.usecase.administration

import uk.co.craigbass.pratura.boundary.administration.AddProduct
import uk.co.craigbass.pratura.boundary.administration.AddProduct.Request
import uk.co.craigbass.pratura.domain.Product

class AddProduct(val productSaver: ProductSaver) : AddProduct {
  override fun execute(request: Request) {
    productSaver.save(Product(request.sku, request.price, request.name))
  }
}
