package modules.providers

import com.google.inject.Inject
import modules.services.IStoreService
import modules.services.NextIdService
import types.models.query.LimitOrderQuery
import types.models.store.LimitOrder
import types.models.store.LimitOrderBook
import java.util.*

interface ILimitOrderBookProvider {
  fun createLimitOrder(req: LimitOrderQuery): UUID
}

class LimitOrderBookProvider @Inject constructor(
  private val storeService: IStoreService,
  private val nextIdService: NextIdService
) : ILimitOrderBookProvider {
  override fun createLimitOrder(req: LimitOrderQuery): UUID {
    val nextId = nextIdService.getNextIdForBook(LimitOrderBook::class)

    return storeService.insertLimitOrder(
      LimitOrder(
        0,
        req.postOnly,
        req.timeInForce,
        UUID.randomUUID(),
        req.price,
        req.quantity,
        req.pair,
        req.side,
        Date(System.currentTimeMillis()),
        nextId,
      )
    )
  }
}
