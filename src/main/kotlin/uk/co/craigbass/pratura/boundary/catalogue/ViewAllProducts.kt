package uk.co.craigbass.pratura.boundary.catalogue

import com.madetech.clean.usecase.SynchronousUseCase
import uk.co.craigbass.pratura.boundary.catalogue.ViewAllProducts.PresentableProduct

interface ViewAllProducts : SynchronousUseCase<Unit, List<PresentableProduct>> {
  data class PresentableProduct(val name: String, val price: String, val sku: String)
}
