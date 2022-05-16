import controllers.IOrderBookController
import controllers.OrderBookController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestOrderBookController {
  private val orderBookController: IOrderBookController = OrderBookController()

  @Test
  fun getOrderHistory_CallsExpectedProviderMethod() {
    assertThrows<NotImplementedError>{ orderBookController.getOrderBook(CurrencyPair.BTCZAR) }
  }
}

