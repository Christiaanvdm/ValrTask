package controllers

import CurrencyPair
import OrderBookResult
import javax.inject.Singleton

interface IOrderBookController {
  fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult
}

@Singleton
class OrderBookController: IOrderBookController {
  override fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult {
    throw NotImplementedError()
  }
}

