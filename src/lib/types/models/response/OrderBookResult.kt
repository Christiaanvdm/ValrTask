package types.models.response

import kotlinx.serialization.Serializable
import types.constants.CurrencyPair
import types.constants.TransactionSide

@Serializable
data class OrderBookResult(
  val lastChangeDate: String,
  val sequenceNumber: Long,
  val asks: List<OrderResult>,
  val bids: List<OrderResult>,
)

@Serializable
data class OrderResult(
  val side: TransactionSide,
  val pair: CurrencyPair,
  val price: Long,
  val quantity: Double,
  val count: Int,
)
