package modules.services

import org.junit.jupiter.api.Test
import types.models.store.IStore
import types.models.store.Order
import types.models.store.OrderBook
import types.models.store.Store
import java.util.UUID
import java.util.Date


class TestStoreService {
  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.00,
    CurrencyPair.BTCZAR,
    TransactionSide.buy,
    Date(System.currentTimeMillis()),
    1
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
        side = TransactionSide.sell, date = Date(System.currentTimeMillis() + 300)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.sell, date = Date(System.currentTimeMillis() + 2000)
      ),
      _order.copy(
        id = UUID.randomUUID(),
        side = TransactionSide.sell,
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
        side = TransactionSide.sell, date = Date(System.currentTimeMillis() + 5000)
      ),
    )
  )

  private val _store: IStore = Store(_orderBook)
  private val _storeService: IStoreService = StoreService(_store)
  private val orders = _orderBook.rows

  //region GetLatestOrderAsksAndBids

  @Test
  fun getLatestOrderAsksAndBids_fullResult_returnsExpectedCurrencyPairs() {
    // ACT
    val result = _storeService.getLatestOrderAsksAndBids(3, CurrencyPair.BTCZAR)

    val asks = result.asks
    val bids = result.bids
    assert(asks.count() == 3)
    assert(bids.count() == 3)

    val expectedAsks = listOf(orders[9], orders[5], orders[3])
    val expectedBids = listOf(orders[8], orders[4], orders[2])
    expectedAsks.forEach { ask -> assert(asks.any { askResult -> askResult.id == ask.id }) }
    expectedBids.forEach { bid -> assert(bids.any { bidResult -> bidResult.id == bid.id }) }
  }

  @Test
  fun getLatestOrderAsksAndBids_notEnoughItems_returnsExpectedCurrencyPairs() {
    val result = _storeService.getLatestOrderAsksAndBids(3, CurrencyPair.ETHZAR)

    val asks = result.asks
    val bids = result.bids
    assert(asks.count() == 1)
    assert(bids.count() == 1)

    assert(asks.any { ask -> ask.id == orders[6].id })
    assert(bids.any { bid -> bid.id == orders[7].id })
  }

  //endregion
}
