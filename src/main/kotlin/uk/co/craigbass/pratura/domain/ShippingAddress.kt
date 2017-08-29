package uk.co.craigbass.pratura.domain

class ShippingAddress(
  val name: String,
  val companyName: String?,
  val addressLine1: String,
  val addressLine2: String?,
  val addressLine3: String?,
  val city: String,
  val province: String,
  val zipcode: String
)
