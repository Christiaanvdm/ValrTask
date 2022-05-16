package types.models.store

import CurrencyPair
import OrderSide
import java.util.Date
import java.util.UUID

class OrderBook(val orders: ArrayList<Order> = ArrayList(0))

data class Order(
    val id: UUID,
    val side: OrderSide,
    val pair: CurrencyPair,
    val quantity: Double,
    val price: Long,
    val count: Int,
    val date: Date,
)

interface IOrderAsksBids {
  val asks: List<Order>
  val bids: List<Order>
}
