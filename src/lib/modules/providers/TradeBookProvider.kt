package modules.providers

import com.google.inject.Inject
import modules.services.IDataService
import types.constants.ECurrencyPair
import types.models.query.Paginator
import types.models.response.TradeResult

interface ITradeBookProvider {
  fun getTradeHistory(query: Paginator, pair: ECurrencyPair): List<TradeResult>
}

class TradeBookProvider @Inject constructor(private val _dataService: IDataService): ITradeBookProvider {
  override fun getTradeHistory(query: Paginator, pair: ECurrencyPair): List<TradeResult> {
    TODO("Implement")
  }
}
