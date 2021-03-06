package controllers

import com.google.inject.Inject
import io.vertx.ext.web.validation.RequestParameters
import kotlinx.coroutines.delay
import modules.helpers.getPathParameter
import modules.helpers.getRequestBody
import modules.providers.ILimitOrderBookProvider
import modules.providers.IOrderBookProvider
import modules.providers.ITradeBookProvider
import types.constants.ECurrencyPair
import types.models.query.TradeHistoryQuery
import types.models.response.LimitOrderResult
import types.models.response.OrderBookResult
import types.models.response.TradeResult

interface ITransactionBooksController {
  val getOrderBook: suspend (params: RequestParameters) -> OrderBookResult
  val postLimitOrder: suspend (params: RequestParameters) -> LimitOrderResult
  val getTradeHistory: suspend (params: RequestParameters) -> List<TradeResult>
}

class TransactionBooksController @Inject constructor(
  private val _orderBookProvider: IOrderBookProvider,
  private val _limitOrderBookProvider: ILimitOrderBookProvider,
  private val _tradeBookProvider: ITradeBookProvider,
) : ITransactionBooksController {
  private fun resolveGetTradeHistoryParameters(params: RequestParameters): TradeHistoryQuery {
    val currencyPair: ECurrencyPair = getPathParameter(params, "currencyPair")
    val skip = params.queryParameter("skip").integer
    val limit = params.queryParameter("limit").integer

    return TradeHistoryQuery(skip, limit, currencyPair)
  }

  override val getOrderBook: suspend (params: RequestParameters) -> OrderBookResult =  {
    delay(4000)
    _orderBookProvider.getOrderHistory(getPathParameter(it, "currencyPair"))
  }

  override val postLimitOrder: suspend (params: RequestParameters) -> LimitOrderResult = {
    _limitOrderBookProvider.createLimitOrder(getRequestBody(it))
  }

  override val getTradeHistory: suspend (params: RequestParameters) -> List<TradeResult> = {
    _tradeBookProvider.getTradeHistory(resolveGetTradeHistoryParameters(it))
  }
}
