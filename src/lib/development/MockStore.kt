package development

import types.constants.ECurrencyPair
import types.constants.EBuySell
import types.models.store.*
import java.util.*

class MockStore : IStore {
  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.00,
    ECurrencyPair.BTCZAR,
    EBuySell.BUY,
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
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 300),
        sequence = 4,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000),
        sequence = 5,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 2000),
        sequence = 6,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL,
        pair = ECurrencyPair.ETHZAR,
        date = Date(System.currentTimeMillis() + 3000),
        sequence = 7,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        pair = ECurrencyPair.ETHZAR, date = Date(System.currentTimeMillis() + 4000),
        sequence = 8,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 4500),
        sequence = 9,
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 5000),
        sequence = 10,
      ),
    )
  )

  private val _trade = Trade(
    1.12345678,
    UUID.randomUUID(),
    1123,
    1.1234,
    ECurrencyPair.BTCZAR,
    EBuySell.SELL,
    Date(System.currentTimeMillis()),
    1,
  )

  private val _tradeBook = TradeBook(
    arrayListOf(
      _trade,
      _trade.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 100),
        sequence = 2,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 200),
        sequence = 3,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 300),
        sequence = 4,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000),
        sequence = 5,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 2000),
        sequence = 6,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL,
        pair = ECurrencyPair.ETHZAR,
        date = Date(System.currentTimeMillis() + 3000),
        sequence = 7,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        pair = ECurrencyPair.ETHZAR, date = Date(System.currentTimeMillis() + 4000),
        sequence = 8,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 4500),
        sequence = 9,
      ),
      _trade.copy(
        id = UUID.randomUUID(),
        side = EBuySell.SELL, date = Date(System.currentTimeMillis() + 5000),
        sequence = 10,
      ),
    )
  )

  override val orderBook: OrderBook = _orderBook
  override val tradeBook: TradeBook = _tradeBook
  override val limitOrderBook: LimitOrderBook = LimitOrderBook()
}
