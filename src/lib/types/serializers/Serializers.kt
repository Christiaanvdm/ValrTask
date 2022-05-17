package types.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import types.constants.EBuySell
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
  override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())

  override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())
}

object BuySellSerializer : KSerializer<EBuySell> {
  override val descriptor = PrimitiveSerialDescriptor("EBuySell", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): EBuySell =
    enumValueOf(decoder.decodeString().uppercase(Locale.getDefault()))

  override fun serialize(encoder: Encoder, value: EBuySell) = encoder.encodeString(
    value.toString().lowercase(Locale.getDefault())
  )
}
