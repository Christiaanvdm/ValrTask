package controllers

import CurrencyPair
import com.google.inject.Inject
import modules.providers.ILimitOrderBookProvider
import modules.providers.IOrderBookProvider
import types.models.query.LimitOrderQuery
import types.models.query.Paginator
import types.models.response.OrderBookResult
import types.models.response.TradeResult
import java.util.*

interface ITransactionBooksController {
  fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult
  fun postLimitOrder(query: LimitOrderQuery): UUID
  fun getTradeHistory(query: Paginator): List<TradeResult>
}

class TransactionBooksController @Inject constructor(
  private val _orderBookProvider: IOrderBookProvider,
  private val _limitOrderBookProvider: ILimitOrderBookProvider,
) : ITransactionBooksController {
  override fun getOrderBook(currencyPair: CurrencyPair): OrderBookResult =
    _orderBookProvider.getOrderHistory(currencyPair)

  override fun postLimitOrder(query: LimitOrderQuery): UUID =
    _limitOrderBookProvider.createLimitOrder(query)

  override fun getTradeHistory(query: Paginator): List<TradeResult> {
    TODO("Not yet implemented")
  }
}

