package modules.providers

import com.google.inject.Inject
import modules.services.IDataService
import types.constants.ECurrencyPair
import types.models.query.Paginator
import types.models.response.TradeResult
import types.models.store.Trade

interface ITradeBookProvider {
  fun getTradeHistory(query: Paginator, pair: ECurrencyPair): List<TradeResult>
}

class TradeBookProvider @Inject constructor(private val _dataService: IDataService): ITradeBookProvider {
  private val mapTradeToTradeResult: (trade: Trade) -> TradeResult = {
    TradeResult(
      it.price,
      it.quantity,
      it.pair,
      it.date.toString(),
      it.side,
      it.sequence,
      it.id,
      it.quoteVolume,
    )
  }

  override fun getTradeHistory(query: Paginator, pair: ECurrencyPair): List<TradeResult> =
    _dataService.getTradeHistory(query.skip, query.limit, pair).map(mapTradeToTradeResult)
}
