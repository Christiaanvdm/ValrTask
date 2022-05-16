package types.models.store

import CurrencyPair
import OrderSide
import java.util.Date
import java.util.UUID

data class OrderBook(var orders: Array<Order> = emptyArray<Order>())

data class Order(
    val id: UUID,
    val side: OrderSide,
    val pair: CurrencyPair,
    val quantity: Double,
    val price: Long,
    val count: Int,
    val date: Date,
)
