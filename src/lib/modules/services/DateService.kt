package modules.services

import com.google.inject.Inject
import types.constants.EBuySell
import types.constants.ECurrencyPair
import types.models.store.*
import java.util.*

interface IDataService {
  fun getLatestOrderAsksAndBids(count: Int, pair: ECurrencyPair): IOrderAsksBids
  fun getTradeHistory(skip: Int, limit: Int, pair: ECurrencyPair): List<Trade>
  fun insertLimitOrder(order: LimitOrder): UUID
}

class DataService @Inject constructor(
  private val _store: IStore,
) : IDataService {
  override fun getLatestOrderAsksAndBids(count: Int, pair: ECurrencyPair): IOrderAsksBids {
    val orders = _store.orderBook.rows.toMutableList()
    orders.sortBy { it.date }

    val asks = mutableListOf<Order>()
    val bids = mutableListOf<Order>()

    while (orders.isNotEmpty() && (asks.count() < count || bids.count() < count)) {
      val lastValue = orders.removeLast()
      if (lastValue.pair != pair)
        continue

      if (lastValue.side == EBuySell.SELL) {
        if (asks.count() < count) asks.add(lastValue)
      } else if (bids.count() < count) bids.add(lastValue)
    }

    return object : IOrderAsksBids {
      override val asks: List<Order> = asks
      override val bids: List<Order> = bids
    }
  }

  override fun getTradeHistory(skip: Int, limit: Int, pair: ECurrencyPair): List<Trade> =
    _store.tradeBook.rows
      .reversed()
      .filter { it.pair == pair }
      .drop(skip)
      .take(limit)

  override fun insertLimitOrder(order: LimitOrder): UUID {
    _store.limitOrderBook.rows.add(order)

    return order.id
  }
}
