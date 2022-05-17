package types.models.response

import kotlinx.serialization.Serializable
import types.constants.EBuySell
import types.constants.ECurrencyPair
import types.serializers.ConfiguredDoubleSerializer

@Serializable
data class OrderBookResult(
  val lastChangeDate: String,
  val sequenceNumber: Long,
  val asks: List<OrderResult>,
  val bids: List<OrderResult>,
)

@Serializable
data class OrderResult(
  val side: EBuySell,
  val pair: ECurrencyPair,
  val price: Long,
  @Serializable(with = ConfiguredDoubleSerializer::class)
  val quantity: Double,
  val count: Int,
)
