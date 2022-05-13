import java.util.Date
import java.util.UUID
import kotlin.emptyArray

class OrderBook(var orders: Array<Order> = emptyArray<Order>())

class Order(
    val id: UUID,
    val side: OrderSide,
    val pair: CurrencyPair,
    val quantity: Double,
    val price: Long,
    val count: Int,
    val date: Date,
)
