package studio.lunabee.monitoring.core

/**
 * Data class that represents a request or a response payload.
 * @param headers headers of the request or response, represented as a string.
 * @param body body of the request or response, represented as a string.
 * @param size size of the request or response in bytes (headers size + body size).
 */
data class LBPayload(
    val headers: String? = null,
    val body: String? = null,
    val size: Long = 0,
)
