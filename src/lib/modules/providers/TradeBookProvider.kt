package modules.providers

import types.models.query.Paginator

interface ITradeBookProvider {
  fun getTradeHistory(query: Paginator)
}

class TradeBookProvider {
}
