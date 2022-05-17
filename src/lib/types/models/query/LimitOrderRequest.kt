package types.models.query

import kotlinx.serialization.Serializable
import types.constants.CurrencyPair
import types.constants.TimeInForce
import types.constants.TransactionSide

@Serializable
data class LimitOrderRequest(
  val side: TransactionSide,
  val quantity: Double,
  val price: Long,
  val pair: CurrencyPair,
  val postOnly: Boolean,
  val customerOrderId: String,
  val timeInForce: TimeInForce,
)
