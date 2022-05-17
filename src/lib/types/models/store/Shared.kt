package types.models.store

import types.constants.ECurrencyPair
import types.constants.EBuySell
import java.util.*

interface IBookTransaction {
  val id: UUID
  val price: Long
  val quantity: Double
  val pair: ECurrencyPair
  val side: EBuySell
  val date: Date
  val sequence: Int
}
