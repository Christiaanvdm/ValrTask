package types.models.query

import types.constants.ECurrencyPair

class TradeHistoryQuery(
  val skip: Int = 0,
  val limit: Int = 10,
  val pair: ECurrencyPair,
)
