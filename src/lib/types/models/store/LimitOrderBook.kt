package types.models.store

import types.constants.ECurrencyPair
import types.constants.ETimeInForce
import types.constants.EBuySell
import java.util.*

class LimitOrderBook(override val rows: ArrayList<LimitOrder> = ArrayList(0)): IBook<LimitOrder>

data class LimitOrder(
  val customerOrderId: String,
  val postOnly: Boolean,
  val timeInForce: ETimeInForce,
  override val id: UUID,
  override val price: Long,
  override val quantity: Double,
  override val pair: ECurrencyPair,
  override val side: EBuySell,
  override val date: Date,
  override val sequence: Int,
) : IBookTransaction

