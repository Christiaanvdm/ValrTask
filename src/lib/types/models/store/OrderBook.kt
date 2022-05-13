import java.util.Date
import java.util.UUID

interface IOrderBook {
    var orders: Array<Order>,
}

class OrderBook(override var orders: Array<Order>): IOrderBook

class Order(
    val id: UUID,
    val side: OrderSide,
    val pair: CurrencyPair,
    val quantity: Double,
    val price: Long,
    val count: Int,
    val date: Date,
)
