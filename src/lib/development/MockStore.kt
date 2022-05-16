package development

import CurrencyPair
import OrderSide
import types.models.store.IStore
import types.models.store.Order
import types.models.store.OrderBook
import java.util.*

class MockStore: IStore {
  private val _order = Order(
    UUID.randomUUID(),
    OrderSide.buy,
    CurrencyPair.BTCZAR,
    1.00,
    1234,
    1,
    Date(System.currentTimeMillis()),
  )

  private val _orderBook = OrderBook(
    arrayListOf(
      _order,
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 100)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 200)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 300)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 2000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell,
        pair = CurrencyPair.ETHZAR,
        date = Date(System.currentTimeMillis() + 3000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        pair = CurrencyPair.ETHZAR, date = Date(System.currentTimeMillis() + 4000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 4500)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 5000)
      ),
    )
  )

  override val orderBook: OrderBook = _orderBook
}
