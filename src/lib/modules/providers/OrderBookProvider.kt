package modules.providers

import CurrencyPair
import com.google.inject.Inject
import modules.helpers.roundToDigits
import modules.services.IStoreService
import types.constants.Configuration
import types.models.response.OrderBookResult
import types.models.response.OrderResult
import types.models.store.IOrderAsksBids
import types.models.store.Order
import java.util.*

interface IOrderBookProvider {
  fun getOrderHistory(pair: CurrencyPair): OrderBookResult
}

open class OrderBookProvider @Inject constructor(private val _storeService: IStoreService) : IOrderBookProvider {
  private fun mapOrderToOrderResult(order: Order): OrderResult =
    OrderResult(order.side, order.pair, order.price, roundToDigits(order.quantity, Configuration.FloatDigitsOutputCount), order.count)

  protected fun getLastIndexAndDate(data: IOrderAsksBids): Pair<Int, Date> {
    val lastAsk = data.asks.last()
    val lastBid = data.bids.last()

    val askDateLatest = lastAsk.date >= lastBid.date
    val lastIndex = if (askDateLatest) _storeService.getOrderIndex(lastAsk) else _storeService.getOrderIndex(lastBid)
    val lastDate = if (askDateLatest) lastAsk.date else lastBid.date

    return Pair(lastIndex, lastDate)
  }

  override fun getOrderHistory(pair: CurrencyPair): OrderBookResult {
    val data = _storeService.getLatestOrderAsksAndBids(Configuration.GetOrderBookQueryAmount, pair)
    val (lastIndex, lastDate) = getLastIndexAndDate(data)

    return OrderBookResult(
      lastDate.toString(),
      lastIndex.toLong(),
      data.asks.map { mapOrderToOrderResult(it) },
      data.bids.map { mapOrderToOrderResult(it) },
    )
  }
}
