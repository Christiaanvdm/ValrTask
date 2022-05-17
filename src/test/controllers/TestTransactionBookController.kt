import controllers.ITransactionBooksController
import controllers.TransactionBooksController
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.vertx.ext.web.validation.RequestParameters
import io.vertx.ext.web.validation.impl.RequestParameterImpl
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
  fun getOrderHistory_CallsExpectedProviderMethod_GetsValueFromParams() {
    val paramsMock = mockk<RequestParameters>()
    every { paramsMock.pathParameter(any()) } returns RequestParameterImpl(ECurrencyPair.ETHZAR.toString())

    val expectedResult: OrderBookResult = mockk()
    every { _orderBookProviderMock.getOrderHistory(any()) } returns expectedResult

    // ACT
    val result = _transactionBookController.getOrderBook(paramsMock)

    verify { _orderBookProviderMock.getOrderHistory(ECurrencyPair.ETHZAR) }
    assert(result == expectedResult)
  }
}

