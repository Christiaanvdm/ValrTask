package types.models.response

import kotlinx.serialization.Serializable
import types.constants.EBuySell
import types.constants.ECurrencyPair
import types.serializers.ConfiguredDoubleSerializer
import types.serializers.UUIDSerializer
import java.util.*

@Serializable
data class TradeResult(
  val price: Long,
  @Serializable(with = ConfiguredDoubleSerializer::class)
  val quantity: Double,
  val currencyPair: ECurrencyPair,
  val tradedAt: String,
  val takerSide: EBuySell,
  val sequenceId: Int,
  @Serializable(with = UUIDSerializer::class)
  val id: UUID,
  @Serializable(with = ConfiguredDoubleSerializer::class)
  val quoteVolume: Double,
)
