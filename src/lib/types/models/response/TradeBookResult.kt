package types.models.response

import CurrencyPair
import TransactionSide
import kotlinx.serialization.Serializable

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
