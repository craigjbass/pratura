package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.ProductSaver
import uk.co.craigbass.pratura.usecase.ProductRetriever

interface ProductGateway : ProductSaver, ProductRetriever
