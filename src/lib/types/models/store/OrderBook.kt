package types.models.store

import types.constants.CurrencyPair
import types.constants.TransactionSide
import java.util.*

class OrderBook(override val rows: ArrayList<Order> = ArrayList(0)): IBook<Order>

data class Order(
  val count: Int,
  override val id: UUID,
  override val price: Long,
  override val quantity: Double,
  override val pair: CurrencyPair,
  override val side: TransactionSide,
  override val date: Date,
  override val sequence: Int,
) : IBookTransaction

interface IOrderAsksBids {
  val asks: List<Order>
  val bids: List<Order>
}
