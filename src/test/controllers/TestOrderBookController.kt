import controllers.IOrderBookController
import controllers.OrderBookController
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import modules.providers.IOrderBookProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import types.models.response.OrderBookResult

class TestOrderBookController {
  private val _orderBookProviderMock = mockk<IOrderBookProvider>()
  private val _orderBookController: IOrderBookController = OrderBookController(_orderBookProviderMock)

  @Test
  fun getOrderHistory_CallsExpectedProviderMethod() {
    val expectedResult: OrderBookResult = mockk()
    every { _orderBookProviderMock.getOrderHistory(any()) } returns expectedResult

    val result = _orderBookController.getOrderBook(CurrencyPair.BTCZAR)

    verify { _orderBookController.getOrderBook(CurrencyPair.BTCZAR) }
    assert(result == expectedResult)
  }
}

