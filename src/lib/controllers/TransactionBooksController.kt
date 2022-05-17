package controllers

import com.google.inject.Inject
import modules.providers.ILimitOrderBookProvider
import modules.providers.IOrderBookProvider
import types.constants.ECurrencyPair
import types.models.query.LimitOrderRequest
import types.models.query.Paginator
import types.models.response.LimitOrderResult
import types.models.response.OrderBookResult
import types.models.response.TradeResult

interface ITransactionBooksController {
  fun getOrderBook(currencyPair: ECurrencyPair): OrderBookResult
  fun postLimitOrder(query: LimitOrderRequest): LimitOrderResult
  fun getTradeHistory(query: Paginator): List<TradeResult>
}

class TransactionBooksController @Inject constructor(
  private val _orderBookProvider: IOrderBookProvider,
  private val _limitOrderBookProvider: ILimitOrderBookProvider,
) : ITransactionBooksController {
  override fun getOrderBook(currencyPair: ECurrencyPair): OrderBookResult =
    _orderBookProvider.getOrderHistory(currencyPair)

  override fun postLimitOrder(query: LimitOrderRequest): LimitOrderResult =
    _limitOrderBookProvider.createLimitOrder(query)

  override fun getTradeHistory(query: Paginator): List<TradeResult> {
    TODO("Not yet implemented")
  }
}

