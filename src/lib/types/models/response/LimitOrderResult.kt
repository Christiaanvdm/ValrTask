package types.models.response

import kotlinx.serialization.Serializable
import types.serializers.UUIDSerializer
import java.util.*

@Serializable
data class LimitOrderResult(
  @Serializable(with = UUIDSerializer::class)
  val id: UUID
)
