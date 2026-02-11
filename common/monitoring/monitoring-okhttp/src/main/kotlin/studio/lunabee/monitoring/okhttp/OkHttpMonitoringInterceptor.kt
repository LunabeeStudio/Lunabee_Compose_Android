package studio.lunabee.monitoring.okhttp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.use
import studio.lunabee.logger.LBLogger
import studio.lunabee.logger.e
import studio.lunabee.monitoring.core.LBMonitoring
import studio.lunabee.monitoring.core.LBPayload
import studio.lunabee.monitoring.core.LBRequest
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val logger = LBLogger.get<OkHttpMonitoringInterceptor>()

/**
 * An interceptor to record OkHttp calls
 */
class OkHttpMonitoringInterceptor(
    private val monitoring: LBMonitoring,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : Interceptor {
    private val prettyJson = Json { prettyPrint = true }
    private val coroutineScope = CoroutineScope(dispatcher + SupervisorJob())

    @OptIn(ExperimentalUuidApi::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body?.let { body ->
            val rawBody = Buffer().use { requestBuffer ->
                body.writeTo(requestBuffer)
                requestBuffer.readString(Charsets.UTF_8)
            }
            kotlin.runCatching {
                prettyJson.encodeToString(prettyJson.parseToJsonElement(rawBody))
            }.onFailure { e ->
                logger.e(e, "Unable to parse body of request.\n$rawBody")
            }.getOrNull() ?: rawBody
        }
        val lbRequest = LBRequest(
            id = Uuid.random(),
            host = request.url.host,
            methodName = request.method,
            queryParams = request.url.encodedQuery,
            outgoingPayload = LBPayload(
                headers = request.headers.toString(),
                body = requestBody,
                size = (request.body?.contentLength() ?: 0) + request.headers.byteCount(),
            ),
            incomingPayload = null,
            statusCode = -1,
            sendingAt = Clock.System.now(),
            duration = 0.milliseconds,
            path = request.url.encodedPath,
        )

        val requestJob = coroutineScope.launch {
            monitoring.upsertRequest(lbRequest)
        }

        val response = chain.proceed(request)

        val source = response.body.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.

        val responseBody = source.buffer.clone().use { responseBuffer ->
            responseBuffer.readString(Charsets.UTF_8)
        }.let { rawBody ->
            kotlin.runCatching {
                prettyJson.encodeToString(prettyJson.parseToJsonElement(rawBody))
            }.onFailure { e ->
                logger.e(e, "Unable to parse body of response.\n$rawBody")
            }.getOrNull() ?: rawBody
        }

        val lbResponse = lbRequest.copy(
            incomingPayload = LBPayload(
                headers = response.headers.toString(),
                body = responseBody,
                size = source.buffer.size + response.headers.byteCount(),
            ),
            statusCode = response.code,
            duration = Clock.System.now() - lbRequest.sendingAt,
        )

        coroutineScope.launch {
            requestJob.join()
            monitoring.upsertRequest(lbResponse)
        }

        return response
    }
}
