package modules.providers

import io.mockk.*
import modules.services.IStoreService
import org.junit.jupiter.api.Test
import types.constants.Configuration
import types.constants.CurrencyPair
import types.constants.TransactionSide
import types.models.store.IOrderAsksBids
import types.models.store.Order
import java.util.*

class OrderBookProviderExposed(_storeServiceMock: IStoreService) : OrderBookProvider(_storeServiceMock) {
  fun getLastSequenceAndDateExposed(data: IOrderAsksBids) = super.getLastSequenceAndDate(data)
}

class TestOrderBookProvider {
  private val _storeServiceMock: IStoreService = mockk()
  private val _exposed = OrderBookProviderExposed(_storeServiceMock)
  private val _orderBook: IOrderBookProvider = _exposed

  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.0012344786,
    CurrencyPair.BTCZAR,
    TransactionSide.SELL,
    Date(System.currentTimeMillis()),
    1,
  )

  private val _orders = arrayListOf(
    _order,
    _order.copy(
      id = UUID.randomUUID(),
      date = Date(System.currentTimeMillis() + 100),
    ),
  )

  private val _asks = _orders.toList().map { ask ->
    ask.copy(
      side = TransactionSide.BUY,
      date = Date(System.currentTimeMillis() + 100),
      sequence = 4,
    )
  }

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
    every { orderBook["getLastSequenceAndDate"](asksBidsMock) } returns Pair(1, date)

    // ACT
    val result = orderBook.getOrderHistory(CurrencyPair.BTCZAR)

    verify { _storeServiceMock.getLatestOrderAsksAndBids(Configuration.GetOrderBookQueryAmount, CurrencyPair.BTCZAR) }
    verify { orderBook["getLastSequenceAndDate"](asksBidsMock) }
    assert(result.lastChangeDate == date.toString())
    assert(result.sequenceNumber == 1.toLong())
    assert(result.asks.first().quantity == 1.001234)
  }

  @Test
  fun getLastIndexAndDate_ReturnsExpectedResults() {
    val asksBidsMock = createAsksBidsMock()

    // ACT
    val (sequence, date) = _exposed.getLastSequenceAndDateExposed(asksBidsMock)

    assert(date == _asks[1].date)
    assert(sequence == 4)
    confirmVerified(_storeServiceMock)
  }

  @Test
  fun getLastIndexAndDate_LastDateIsBid_ReturnsExpectedResults() {
    val dataMock = mockk<IOrderAsksBids>()
    val asks = _orders.toList()
    val bids = _orders.toList()
      .map { ask ->
        ask.copy(
          side = TransactionSide.BUY,
          date = Date(System.currentTimeMillis() + 100),
          sequence = 4,
        )
      }
    every { dataMock.asks } returns asks
    every { dataMock.bids } returns bids

    // ACT
    val (sequence, date) = _exposed.getLastSequenceAndDateExposed(dataMock)

    assert(date == bids[1].date)
    assert(sequence == 4)
    confirmVerified(_storeServiceMock)
  }
}
