package modules.providers

import io.mockk.*
import modules.services.IDataService
import org.junit.jupiter.api.Test
import types.constants.Configuration
import types.constants.EBuySell
import types.constants.ECurrencyPair
import types.models.store.IOrderAsksBids
import types.models.store.Order
import java.util.*

class OrderBookProviderExposed(_dataServiceMock: IDataService) : OrderBookProvider(_dataServiceMock) {
  fun getLastSequenceAndDateExposed(data: IOrderAsksBids) = super.getLastSequenceAndDate(data)
}

class TestOrderBookProvider {
  private val _dataServiceMock: IDataService = mockk()
  private val _exposed = OrderBookProviderExposed(_dataServiceMock)
  private val _orderBook: IOrderBookProvider = _exposed

  private val _order = Order(
    1,
    UUID.randomUUID(),
    1234,
    1.0012344786,
    ECurrencyPair.BTCZAR,
    EBuySell.SELL,
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
      side = EBuySell.BUY,
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
    every { _dataServiceMock.getLatestOrderAsksAndBids(any(), any()) } returns asksBidsMock

    val date = Date()
    val orderBook = spyk(_orderBook, recordPrivateCalls = true)
    every { orderBook["getLastSequenceAndDate"](asksBidsMock) } returns Pair(1, date)

    // ACT
    val result = orderBook.getOrderHistory(ECurrencyPair.BTCZAR)

    verify { _dataServiceMock.getLatestOrderAsksAndBids(Configuration.GetOrderBookQueryAmount, ECurrencyPair.BTCZAR) }
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
    confirmVerified(_dataServiceMock)
  }

  @Test
  fun getLastIndexAndDate_LastDateIsBid_ReturnsExpectedResults() {
    val dataMock = mockk<IOrderAsksBids>()
    val asks = _orders.toList()
    val bids = _orders.toList()
      .map { ask ->
        ask.copy(
          side = EBuySell.BUY,
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
    confirmVerified(_dataServiceMock)
  }
}
