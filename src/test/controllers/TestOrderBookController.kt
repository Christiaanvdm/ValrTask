import controllers.IOrderBookController
import controllers.OrderBookController
import io.mockk.mockk
import modules.providers.IOrderBookProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestOrderBookController {
  private val orderBookProviderMock = mockk<IOrderBookProvider>()
  private val orderBookController: IOrderBookController = OrderBookController(orderBookProviderMock)

  @Test
  fun getOrderHistory_CallsExpectedProviderMethod() {
    assertThrows<NotImplementedError>{ orderBookController.getOrderBook(CurrencyPair.BTCZAR) }
  }
}

