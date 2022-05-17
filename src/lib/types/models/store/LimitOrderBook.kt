package types.models.store

import types.constants.CurrencyPair
import types.constants.TimeInForce
import types.constants.TransactionSide
import java.util.*

class LimitOrderBook(override val rows: ArrayList<LimitOrder> = ArrayList(0)): IBook<LimitOrder>

data class LimitOrder(
  val customerOrderId: String,
  val postOnly: Boolean,
  val timeInForce: TimeInForce,
  override val id: UUID,
  override val price: Long,
  override val quantity: Double,
  override val pair: CurrencyPair,
  override val side: TransactionSide,
  override val date: Date,
  override val sequence: Int,
) : IBookTransaction

