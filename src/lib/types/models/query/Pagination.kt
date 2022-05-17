package types.models.query

import kotlinx.serialization.Serializable

@Serializable
data class Paginator(
  val skip: Int = 0,
  val limit: Int = 10
)
