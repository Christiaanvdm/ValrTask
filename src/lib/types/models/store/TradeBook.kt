package types.models.store

import types.constants.CurrencyPair
import types.constants.TransactionSide
import java.util.*

class TradeBook(override val rows: ArrayList<Trade> = ArrayList(0)): IBook<Trade>

data class Trade(
  val quoteVolume: Double,
  override val id: UUID,
  override val price: Long,
  override val quantity: Double,
  override val pair: CurrencyPair,
  override val side: TransactionSide,
  override val date: Date,
  override val sequence: Int,
) : IBookTransaction
