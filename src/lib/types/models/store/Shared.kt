package types.models.store

import types.constants.CurrencyPair
import types.constants.TransactionSide
import java.util.*

interface IBookTransaction {
  val id: UUID
  val price: Long
  val quantity: Double
  val pair: CurrencyPair
  val side: TransactionSide
  val date: Date
  val sequence: Int
}
