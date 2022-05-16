package types.models.store

import javax.inject.Singleton

interface IStore {
  val orderBook: OrderBook
}

@Singleton
class Store: IStore {
  override val orderBook: OrderBook = OrderBook()
}

