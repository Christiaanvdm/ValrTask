import controllers.ITransactionBooksController
import controllers.TransactionBooksController
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import modules.providers.ILimitOrderBookProvider
import modules.providers.IOrderBookProvider
import modules.providers.ITradeBookProvider
import org.junit.jupiter.api.Test
import types.constants.ECurrencyPair
import types.models.response.OrderBookResult

class TestTransactionBookController {
  private val _orderBookProviderMock = mockk<IOrderBookProvider>()
  private val _limitOrderBookProviderMock = mockk<ILimitOrderBookProvider>()
  private val _tradeBookProviderMock = mockk<ITradeBookProvider>()

  private val _transactionBookController: ITransactionBooksController = TransactionBooksController(
    _orderBookProviderMock,
    _limitOrderBookProviderMock,
    _tradeBookProviderMock,
  )

  @Test
  fun getOrderHistory_CallsExpectedProviderMethod() {
    val expectedResult: OrderBookResult = mockk()
    every { _orderBookProviderMock.getOrderHistory(any()) } returns expectedResult

    val result = _transactionBookController.getOrderBook(ECurrencyPair.BTCZAR)

    verify { _transactionBookController.getOrderBook(ECurrencyPair.BTCZAR) }
    assert(result == expectedResult)
  }
}

