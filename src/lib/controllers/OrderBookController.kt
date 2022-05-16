package controllers

import CurrencyPair
import com.google.inject.Inject
import modules.providers.IOrderBookProvider
import types.models.response.OrderBookResult
import javax.inject.Singleton

interface IOrderBookController {
  fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult
}

@Singleton
class OrderBookController @Inject constructor(private val provider: IOrderBookProvider) : IOrderBookController {
  override fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult {
    throw NotImplementedError()
  }
}

