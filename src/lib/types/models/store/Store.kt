package types.models.store

import javax.inject.Singleton

interface IStore {
  val orderBook: OrderBook
  val tradeBook: TradeBook
  val limitOrderBook: LimitOrderBook
}

interface IBook<T> where T : IBookTransaction {
  val rows: ArrayList<T>
}

@Singleton
class Store(
  override val orderBook: OrderBook = OrderBook(),
  override val tradeBook: TradeBook = TradeBook(),
  override val limitOrderBook: LimitOrderBook = LimitOrderBook()
) : IStore
