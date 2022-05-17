package types.models.response

import kotlinx.serialization.Serializable
import types.constants.CurrencyPair
import types.constants.TransactionSide

@Serializable
data class TradeResult(
  val price: Long,
  val quantity: Int,
  val currencyPair: CurrencyPair,
  val tradedAt: String,
  val takerSide: TransactionSide,
  val sequenceId: Int,
  val id: String,
  val quoteVolume: Double,
)
