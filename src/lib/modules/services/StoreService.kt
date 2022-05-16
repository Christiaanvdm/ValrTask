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
    TODO("Not yet implemented")
  }
}
