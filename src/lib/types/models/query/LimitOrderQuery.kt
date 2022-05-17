package types.models.query

import CurrencyPair
import TimeInForce
import TransactionSide
import kotlinx.serialization.Serializable

@Serializable
data class LimitOrderQuery(
  val side: TransactionSide,
  val quantity: Double,
  val price: Long,
  val pair: CurrencyPair,
  val postOnly: Boolean,
  val customerOrderId: Long,
  val timeInForce: TimeInForce,
)
