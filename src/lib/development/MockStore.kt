package development

import types.constants.CurrencyPair
import types.constants.TransactionSide
import types.models.store.*
import java.util.*

class MockStore : IStore {
  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.00,
    CurrencyPair.BTCZAR,
    TransactionSide.BUY,
    Date(System.currentTimeMillis()),
    1,
  )

  private val _orderBook = OrderBook(
    arrayListOf(
      _order,
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 100),
        sequence = 2,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 200),
        sequence = 3,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.SELL, date = Date(System.currentTimeMillis() + 300),
        sequence = 4,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000),
        sequence = 5,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.SELL, date = Date(System.currentTimeMillis() + 2000),
        sequence = 6,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.SELL,
        pair = CurrencyPair.ETHZAR,
        date = Date(System.currentTimeMillis() + 3000),
        sequence = 7,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        pair = CurrencyPair.ETHZAR, date = Date(System.currentTimeMillis() + 4000),
        sequence = 8,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 4500),
        sequence = 9,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.SELL, date = Date(System.currentTimeMillis() + 5000),
        sequence = 10,
      ),
    )
  )

  override val orderBook: OrderBook = _orderBook
  override val tradeBook: TradeBook = TradeBook()
  override val limitOrderBook: LimitOrderBook = LimitOrderBook()
}
