package modules.services

import com.google.inject.Inject
import types.models.store.*
import kotlin.reflect.KClass

interface INextIdService {
  fun <U, T : IBook<U>> getNextIdForBook(type: KClass<T>): Int
}

class NextIdService @Inject constructor(private val _store: IStore) : INextIdService {
  override fun <U, T : IBook<U>> getNextIdForBook(type: KClass<T>): Int {
    return when (type) {
      OrderBook::class -> _store.orderBook.rows.count() + 1
      TradeBook::class -> _store.tradeBook.rows.count() + 1
      LimitOrderBook::class -> _store.limitOrderBook.rows.count() + 1
      else -> throw NotImplementedError("Id context unresolved")
    }
  }
}
