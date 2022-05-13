interface IDataLayerService {
  fun getOrdersByCurrencyPair(currencyPair: CurrencyPair): OrderBook
}