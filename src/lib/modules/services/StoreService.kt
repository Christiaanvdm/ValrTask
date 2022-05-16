package modules.services

import CurrencyPair
import com.google.inject.Inject
import types.models.store.IStore
import types.models.store.Order

interface IStoreService {
  fun getLatestOrderAsksAndBids(count: Long, pair: CurrencyPair): Pair<List<Order>, List<Order>>
}

class StoreService @Inject constructor(private val store: IStore): IStoreService {
  override fun getLatestOrderAsksAndBids(count: Long, pair: CurrencyPair): Pair<List<Order>, List<Order>> {
    val orders = store.orderBook.orders.toMutableList();
    val asks = mutableListOf<Order>()
    val bids = mutableListOf<Order>()

    while (orders.isNotEmpty() && (asks.count() < count || bids.count() < count)) {
      val lastValue = orders.removeLast()
      if (lastValue.pair != pair)
        continue

      if (lastValue.side == OrderSide.sell) {
        if (asks.count() < count) asks.add(lastValue)
      }
      else if (bids.count() < count) bids.add(lastValue)
    }

    return Pair(asks, bids)
  }
}
