package types.models.store

import CurrencyPair
import TimeInForce
import TransactionSide
import java.util.Date
import java.util.UUID

class LimitOrderBook(override val rows: ArrayList<LimitOrder> = ArrayList(0)): IBook<LimitOrder>

data class LimitOrder(
  val customerId: Int,
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

