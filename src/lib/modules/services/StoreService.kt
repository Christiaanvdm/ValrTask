package modules.services

import CurrencyPair
import com.google.inject.Inject
import types.models.store.*
import java.util.UUID

interface IStoreService {
  fun getLatestOrderAsksAndBids(count: Long, pair: CurrencyPair): IOrderAsksBids
  fun insertLimitOrder(order: LimitOrder): UUID
}

class StoreService @Inject constructor(
  private val _store: IStore,
) : IStoreService {
  override fun getLatestOrderAsksAndBids(count: Long, pair: CurrencyPair): IOrderAsksBids {
    val orders = _store.orderBook.rows.toMutableList()
    orders.sortBy { it.date }

    val asks = mutableListOf<Order>()
    val bids = mutableListOf<Order>()

    while (orders.isNotEmpty() && (asks.count() < count || bids.count() < count)) {
      val lastValue = orders.removeLast()
      if (lastValue.pair != pair)
        continue

      if (lastValue.side == TransactionSide.sell) {
        if (asks.count() < count) asks.add(lastValue)
      } else if (bids.count() < count) bids.add(lastValue)
    }

    return object : IOrderAsksBids {
      override val asks: List<Order> = asks
      override val bids: List<Order> = bids
    }
  }

  override fun insertLimitOrder(order: LimitOrder): UUID {
    _store.limitOrderBook.rows.add(order)

    return order.id
  }
}
