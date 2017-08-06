package uk.co.craigbass.pratura.gateway

import uk.co.craigbass.pratura.usecase.ProductRetriever
import uk.co.craigbass.pratura.usecase.administration.ProductSaver

interface ProductGateway : ProductSaver, ProductRetriever
