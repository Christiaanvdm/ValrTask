package controllers

import CurrencyPair
import com.google.inject.Inject
import modules.providers.IOrderBookProvider
import types.models.response.OrderBookResult

interface IOrderBookController {
  fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult
}

class OrderBookController @Inject constructor(private val _provider: IOrderBookProvider) : IOrderBookController {
  override fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult =
    _provider.getOrderHistory(currencyPair)
}

