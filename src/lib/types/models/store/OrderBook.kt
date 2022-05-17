package types.models.store

import types.constants.ECurrencyPair
import types.constants.EBuySell
import java.util.*

class OrderBook(override val rows: ArrayList<Order> = ArrayList(0)): IBook<Order>

data class Order(
  val count: Int,
  override val id: UUID,
  override val price: Long,
  override val quantity: Double,
  override val pair: ECurrencyPair,
  override val side: EBuySell,
  override val date: Date,
  override val sequence: Int,
) : IBookTransaction

interface IOrderAsksBids {
  val asks: List<Order>
  val bids: List<Order>
}
