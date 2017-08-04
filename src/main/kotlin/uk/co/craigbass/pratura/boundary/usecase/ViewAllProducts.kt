package uk.co.craigbass.pratura.boundary.usecase

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.usecase.ViewAllProducts.PresentableProduct

interface ViewAllProducts : SynchronousUseCase<Unit, List<PresentableProduct>> {
  data class PresentableProduct(val name: String, val price: String, val sku: String)
}
