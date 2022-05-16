package modules.services

import org.junit.jupiter.api.Test
import types.models.store.IStore
import types.models.store.Order
import types.models.store.OrderBook
import types.models.store.Store
import java.util.UUID
import java.util.Date
import kotlin.collections.ArrayDeque


class TestStoreService {
  private val order = Order(
    UUID.randomUUID(),
    OrderSide.buy,
    CurrencyPair.BTCZAR,
    1.00,
    1234,
    1,
    Date(System.currentTimeMillis()),
  )
  private val orderBook = OrderBook(
    arrayListOf(
      order,
      order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 100)
      ),
      order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 200)
      ),
      order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 300)
      ),
      order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 1000)
      ),
      order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 2000)
      ),
      order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell,
        pair = CurrencyPair.ETHZAR,
        date = Date(System.currentTimeMillis() + 3000)
      ),
      order.copy(
        id = UUID.randomUUID(),
        pair = CurrencyPair.ETHZAR, date = Date(System.currentTimeMillis() + 4000)
      ),
      order.copy(
        id = UUID.randomUUID(),
        date = Date(System.currentTimeMillis() + 4500)
      ),
      order.copy(
        id = UUID.randomUUID(),
        side = OrderSide.sell, date = Date(System.currentTimeMillis() + 5000)
      ),
    )
  )
  private val _store: IStore = Store(orderBook)
  private val _storeService: IStoreService = StoreService(_store)
  private val orders = orderBook.orders

  //region GetLatestOrderAsksAndBids

  @Test
  fun getLatestOrderAsksAndBids_fullResult_returnsExpectedCurrencyPairs() {
    val (asks, bids) = _storeService.getLatestOrderAsksAndBids(3, CurrencyPair.BTCZAR)

    assert(asks.count() == 3)
    assert(bids.count() == 3)

    val expectedAsks = listOf(orders[9], orders[5], orders[3])
    val expectedBids = listOf(orders[8], orders[4], orders[2])
    expectedAsks.forEach { ask -> assert(asks.any { askResult -> askResult.id == ask.id }) }
    expectedBids.forEach { bid -> assert(bids.any { bidResult -> bidResult.id == bid.id }) }
  }

  @Test
  fun getLatestOrderAsksAndBids_notEnoughItems_returnsExpectedCurrencyPairs() {
    val (asks, bids) = _storeService.getLatestOrderAsksAndBids(3, CurrencyPair.ETHZAR)

    assert(asks.count() == 1)
    assert(bids.count() == 1)

    assert(asks.any { ask -> ask.id == orders[6].id })
    assert(bids.any { bid -> bid.id == orders[7].id })
  }

  //endregion
}
