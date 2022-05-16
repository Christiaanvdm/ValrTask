package modules.providers

import CurrencyPair
import com.google.inject.Inject
import modules.services.IStoreService
import types.models.response.OrderBookResult

interface  IOrderBookProvider {
  fun getOrderHistory(pair: CurrencyPair): OrderBookResult
}

open class OrderBookProvider @Inject constructor(private val storeService: IStoreService) : IOrderBookProvider {
  override fun getOrderHistory(pair: CurrencyPair): OrderBookResult {
    TODO("Not yet implemented")
  }
}
