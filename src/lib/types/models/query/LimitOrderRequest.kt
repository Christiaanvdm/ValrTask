package types.models.query

import kotlinx.serialization.Serializable
import types.constants.ECurrencyPair
import types.constants.ETimeInForce
import types.constants.EBuySell

@Serializable
data class LimitOrderRequest(
  val side: EBuySell,
  val quantity: Double,
  val price: Long,
  val pair: ECurrencyPair,
  val postOnly: Boolean,
  val customerOrderId: String,
  val timeInForce: ETimeInForce,
)
