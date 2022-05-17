package types.models.response

import kotlinx.serialization.Serializable
import types.constants.EBuySell
import types.constants.ECurrencyPair

@Serializable
data class TradeResult(
  val price: Long,
  val quantity: Int,
  val currencyPair: ECurrencyPair,
  val tradedAt: String,
  val takerSide: EBuySell,
  val sequenceId: Int,
  val id: String,
  val quoteVolume: Double,
)
