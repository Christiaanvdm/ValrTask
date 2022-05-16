package types.models.store

import javax.inject.Singleton

interface IStore {
  val orderBook: OrderBook
}

@Singleton
class Store(orderBook: OrderBook = OrderBook()): IStore {
  override val orderBook: OrderBook = orderBook
}
