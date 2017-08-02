package uk.co.craigbass.pratura.boundary

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.AddProduct.Request
import java.math.BigDecimal

interface AddProduct : SynchronousUseCase<Request, Unit>{
  data class Request(val sku: String, val price: BigDecimal, val name: String)
}