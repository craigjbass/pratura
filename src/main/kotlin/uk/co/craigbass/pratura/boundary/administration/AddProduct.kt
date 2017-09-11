package uk.co.craigbass.pratura.boundary.administration

import com.madetech.clean.usecase.SynchronousUseCase
import java.math.BigDecimal

interface AddProduct : SynchronousUseCase<AddProduct.Request, Unit> {
  data class Request(
    val sku: String,
    val price: BigDecimal,
    val name: String
  )
}
