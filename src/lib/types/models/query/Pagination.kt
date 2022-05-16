import kotlinx.serialization.Serializable

@Serializable
data class Paginator(
    var skip: Long = 0,
    var limit: Int = 10
)
