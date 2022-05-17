package modules.providers

import com.google.inject.Inject
import modules.services.IDataService
import modules.services.NextIdService
import types.models.query.LimitOrderRequest
import types.models.response.LimitOrderResult
import types.models.store.LimitOrder
import types.models.store.LimitOrderBook
import java.util.*

interface ILimitOrderBookProvider {
  fun createLimitOrder(request: LimitOrderRequest): LimitOrderResult
}

class LimitOrderBookProvider @Inject constructor(
  private val _dataService: IDataService,
  private val _nextIdService: NextIdService,
) : ILimitOrderBookProvider {
  override fun createLimitOrder(request: LimitOrderRequest): LimitOrderResult {
    val nextId = _nextIdService.getNextIdForBook(LimitOrderBook::class)

    val id = _dataService.insertLimitOrder(
      LimitOrder(
        "0",
        request.postOnly,
        request.timeInForce,
        UUID.randomUUID(),
        request.price,
        request.quantity,
        request.pair,
        request.side,
        Date(System.currentTimeMillis()),
        nextId,
      ))

    return LimitOrderResult(id)
  }
}
