package modules.providers

import com.google.inject.Inject
import modules.helpers.roundToDigits
import modules.services.IDataService
import types.constants.Configuration
import types.constants.ECurrencyPair
import types.models.response.OrderBookResult
import types.models.response.OrderResult
import types.models.store.IOrderAsksBids
import types.models.store.Order
import java.util.*

interface IOrderBookProvider {
  fun getOrderHistory(pair: ECurrencyPair): OrderBookResult
}

open class OrderBookProvider @Inject constructor(private val _dataService: IDataService) : IOrderBookProvider {
  private val mapOrderToOrderResult: (order: Order) -> OrderResult = {
    OrderResult(
      it.side,
      it.pair,
      it.price,
      roundToDigits(it.quantity, Configuration.OutputFloatPrecision),
      it.count,
    )
  }

  protected fun getLastSequenceAndDate(data: IOrderAsksBids): Pair<Int, Date> {
    val lastAsk = data.asks.first()
    val lastBid = data.bids.first()

    val askSequenceLatest = lastAsk.sequence >= lastBid.sequence
    val lastSequence = if (askSequenceLatest) lastAsk.sequence else lastBid.sequence
    val lastDate = if (askSequenceLatest) lastAsk.date else lastBid.date

    return Pair(lastSequence, lastDate)
  }

  override fun getOrderHistory(pair: ECurrencyPair): OrderBookResult {
    val data = _dataService.getLatestOrderAsksAndBids(Configuration.GetOrderBookQueryAmount, pair)
    val (lastIndex, lastDate) = getLastSequenceAndDate(data)

    return OrderBookResult(
      lastDate.toString(),
      lastIndex.toLong(),
      data.asks.map(mapOrderToOrderResult),
      data.bids.map(mapOrderToOrderResult),
    )
  }
}
