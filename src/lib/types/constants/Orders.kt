package types.constants

import kotlinx.serialization.Serializable
import types.serializers.BuySellSerializer

@Serializable(with = BuySellSerializer::class)
enum class EBuySell {
  BUY,
  SELL,
}

enum class ECurrencyPair {
  BTCZAR,
  ETHZAR,
  XRPZAR,
}

enum class ETimeInForce {
  GTC,
  IOC,
  FOK,
}
