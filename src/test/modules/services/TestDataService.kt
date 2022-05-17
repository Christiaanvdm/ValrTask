package modules.services

import org.junit.jupiter.api.Test
import types.constants.ECurrencyPair
import types.constants.EBuySell
import types.models.store.*
import java.util.*


class TestDataService {
  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.00,
    ECurrencyPair.BTCZAR,
    EBuySell.BUY,
    Date(System.currentTimeMillis()),
    1
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

  private val _store: IStore = Store(_orderBook, _tradeBook)
  private val _dataService: IDataService = DataService(_store)

  private val _orders = _orderBook.rows
  private val _trades = _tradeBook.rows

  //region GetLatestOrderAsksAndBids

  @Test
  fun getLatestOrderAsksAndBids_fullResult_returnsExpectedCurrencyPairs() {
    // ACT
    val result = _dataService.getLatestOrderAsksAndBids(3, ECurrencyPair.BTCZAR)

    val asks = result.asks
    val bids = result.bids
    assert(asks.count() == 3)
    assert(bids.count() == 3)

    val expectedAsks = listOf(_orders[9], _orders[5], _orders[3])
    val expectedBids = listOf(_orders[8], _orders[4], _orders[2])
    expectedAsks.forEach { ask -> assert(asks.any { askResult -> askResult.id == ask.id }) }
    expectedBids.forEach { bid -> assert(bids.any { bidResult -> bidResult.id == bid.id }) }
  }

  @Test
  fun getLatestOrderAsksAndBids_notEnoughItems_returnsExpectedCurrencyPairs() {
    val result = _dataService.getLatestOrderAsksAndBids(3, ECurrencyPair.ETHZAR)

    val asks = result.asks
    val bids = result.bids
    assert(asks.count() == 1)
    assert(bids.count() == 1)

    assert(asks.any { ask -> ask.id == _orders[6].id })
    assert(bids.any { bid -> bid.id == _orders[7].id })
  }

  //endregion

  //region getTradeHistory

  @Test
  fun getOrderHistory_fullResult_ReturnsExpected() {
    val result = _dataService.getTradeHistory(2, 4, ECurrencyPair.BTCZAR)

    assert(result.count() == 4)
    assert(result.containsAll(listOf(_trades[5], _trades[4], _trades[3], _trades[2])))
  }

  @Test
  fun getOrderHistory_partialResult_ReturnsExpected() {
    val result = _dataService.getTradeHistory(1, 2, ECurrencyPair.ETHZAR)

    assert(result.count() == 1)
    assert(result.contains(_trades[6]))
  }

  @Test
  fun getOrderHistory_skipExceeds_ReturnsExpected() {
    val result = _dataService.getTradeHistory(2, 2, ECurrencyPair.ETHZAR)

    assert(result.isEmpty())
  }

  //endregion
}
