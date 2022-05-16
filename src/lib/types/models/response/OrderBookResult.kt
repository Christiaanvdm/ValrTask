package types.models.response

import CurrencyPair
import OrderSide
import kotlinx.serialization.Serializable

@Serializable
data class OrderBookResult(
  var lastChangeDate: String,
  var sequenceNumber: Long,
  var asks: Array<OrderResult>,
  var bids: Array<OrderResult>,
)

@Serializable
data class OrderResult(
  val side: OrderSide,
  val pair: CurrencyPair,
  val price: Long,
  val quantity: Double,
  val count: Int,
)
