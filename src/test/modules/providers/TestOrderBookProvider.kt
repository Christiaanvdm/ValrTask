package modules.providers

import CurrencyPair
import OrderSide
import io.mockk.*
import modules.services.IStoreService
import org.junit.jupiter.api.Test
import types.constants.Configuration
import types.models.store.IOrderAsksBids
import types.models.store.Order
import java.util.*

class OrderBookProviderExposed(_storeServiceMock: IStoreService) : OrderBookProvider(_storeServiceMock) {
  fun getLastIndexAndDateExposed(data: IOrderAsksBids) = super.getLastIndexAndDate(data)
}

class TestOrderBookProvider {
  private val _storeServiceMock: IStoreService = mockk()
  private val _exposed = OrderBookProviderExposed(_storeServiceMock)
  private val _orderBook: IOrderBookProvider = _exposed

  private val _order = Order(
    UUID.randomUUID(),
    OrderSide.sell,
    CurrencyPair.BTCZAR,
    1.001234,
    1234,
    1,
    Date(System.currentTimeMillis()),
  )

  private val _orders = arrayListOf(
    _order,
    _order.copy(
      id = UUID.randomUUID(),
      date = Date(System.currentTimeMillis() + 100),
    ),
  )

  private val _asks = _orders.toList().map{ ask -> ask.copy(side = OrderSide.buy, date = Date(System.currentTimeMillis() + 100))}
  private val _bids = _orders.toList()

  private fun createAsksBidsMock(): IOrderAsksBids {
    val dataMock = mockk<IOrderAsksBids>()
    every { dataMock.asks } returns _asks
    every { dataMock.bids } returns _bids

    return dataMock
  }

  @Test
  fun getOrderHistory_compilesDataAsExpected() {
    val asksBidsMock = createAsksBidsMock()

    every { _storeServiceMock.getLatestOrderAsksAndBids(any(), any()) } returns asksBidsMock

    val date = Date()
    val orderBook = spyk(_orderBook, recordPrivateCalls = true)
    every { orderBook["getLastIndexAndDate"](asksBidsMock) } returns Pair(1, date)

    // ACT
    val result = orderBook.getOrderHistory(CurrencyPair.BTCZAR)

    verify { _storeServiceMock.getLatestOrderAsksAndBids(Configuration.GetOrderBookQueryAmount, CurrencyPair.BTCZAR) }
    verify { orderBook["getLastIndexAndDate"](asksBidsMock) }
    assert(result.lastChangeDate == date.toString())
    assert(result.sequenceNumber == 1.toLong())
    assert(result.asks.first().quantity == 1.00123)
  }

  @Test
  fun getLastIndexAndDate_ReturnsExpectedResults() {
    val asksBidsMock = createAsksBidsMock()
    every { _storeServiceMock.getOrderIndex(_asks[1]) } returns 4

    // ACT
    val (index, date) = _exposed.getLastIndexAndDateExposed(asksBidsMock)

    assert(date == _asks[1].date)
    assert(index == 4)
    verify { _storeServiceMock.getOrderIndex(_asks[1]) }
    confirmVerified(_storeServiceMock)
  }

  @Test
  fun getLastIndexAndDate_LastDateIsBid_ReturnsExpectedResults() {
    val dataMock = mockk<IOrderAsksBids>()
    val asks = _orders.toList()
    val bids = _orders.toList().map{ ask -> ask.copy(side = OrderSide.buy, date = Date(System.currentTimeMillis() + 100))}
    every { dataMock.asks } returns asks
    every { dataMock.bids } returns bids
    every { _storeServiceMock.getOrderIndex(bids[1]) } returns 4

    // ACT
    val (index, date) = _exposed.getLastIndexAndDateExposed(dataMock)

    assert(date == bids[1].date)
    assert(index == 4)
    verify { _storeServiceMock.getOrderIndex(bids[1]) }
    confirmVerified(_storeServiceMock)
  }
}
