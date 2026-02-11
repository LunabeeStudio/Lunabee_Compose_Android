/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.lunabee.compose.demo.monitoring

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import studio.lunabee.ktor.LBKtorException
import studio.lunabee.ktor.LBKtorExceptionHandler
import studio.lunabee.ktor.LBKtorJson
import studio.lunabee.ktor.LBKtorKermit
import studio.lunabee.logger.LBLogger
import studio.lunabee.monitoring.ktor.LBKtorMonitoring

@Composable
fun KtorDemoScreen(
    viewModel: KtorDemoViewModel = viewModel(),
) {
    val dogFact: String? by viewModel.factDog.collectAsState()
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) { Text(text = dogFact ?: "Click on any buttons!") }
        val testActions = listOf(
            "Get dog fact" to viewModel::getDogFact,
            "Get dog fact as json" to viewModel::getDogFactAsJson,
            "Get dog fact with error" to viewModel::getDogFactWithErrorCatching,
            "Post with error" to viewModel::postDogFactWithErrorNotCaught,
            "No endpoint" to viewModel::noEndpoint,
            "See logs" to { NetworkLogActivity.startActivity(context = context) },
        )
        items(items = testActions) { (text, action) -> Button(onClick = action) { Text(text = text) } }
    }
}

class RemoteDogException(cause: Throwable, val body: String? = null) : Exception(cause)

// For demo purpose. This is applicationContext.
class KtorDemoViewModel : ViewModel() {
    private val _factDog: MutableStateFlow<String?> = MutableStateFlow(value = null)
    val factDog: StateFlow<String?> = _factDog.asStateFlow()

    private val httpClient: HttpClient = HttpClient(Android) {
        install(LBKtorJson) { url = "https://dogapi.dog" }
        install(LBKtorExceptionHandler) {
            mapErr = { e ->
                when (e) {
                    is LBKtorException.Decoding,
                    is LBKtorException.IO,
                    is LBKtorException.Unexpected,
                    -> e

                    is LBKtorException.ServerResponse -> RemoteDogException(e, e.response.bodyAsText())
                }
            }
        }
        install(LBKtorKermit)
        install(HttpTimeout) {
            requestTimeoutMillis = 5_000
            connectTimeoutMillis = 5_000
        }
        install(LBKtorMonitoring) {
            monitoring = LBMonitoringDemo.monitoring
        }
    }

    fun getDogFact() {
        viewModelScope.launch {
            _factDog.value = httpClient
                .get(urlString = "api/v2/facts") { parameter("limit", 1) }
                .body<ApiDogFact>()
                .data.first().attributes.body
        }
    }

    fun getDogFactAsJson() {
        viewModelScope.launch {
            _factDog.value = httpClient.get(urlString = "api/v2/facts") {
                parameter("limit", 1)
            }.bodyAsText()
        }
    }

    fun getDogFactWithErrorCatching() {
        viewModelScope.launch {
            _factDog.value = try {
                // i.e is like our runCatching in domain.
                httpClient.get(urlString = "api/v2/facts") { parameter("limit", 1) }
                    .body<ApiDogFactUnexpected>().data.first().attributes.body
            } catch (e: LBKtorException) {
                e.message ?: "error"
            }
        }
    }

    fun postDogFactWithErrorNotCaught() {
        viewModelScope.launch {
            _factDog.value = try {
                // i.e is like our runCatching in domain.
                httpClient.post(urlString = "api/v2/facts") {
                    setBody(LBLogger.get("")) // try to send a non-serializable object: we do not catch the error as it is a dev error.
                }.body<ApiDogFactUnexpected>()
                    .data.first().attributes.body
            } catch (e: LBKtorException) {
                e.message ?: "error"
            }
        }
    }

    fun noEndpoint() {
        viewModelScope.launch {
            _factDog.value = try {
                // i.e is like our runCatching in domain.
                httpClient
                    .get(urlString = "api/fac")
                    .body<ApiDogFact>()
                    .data.first().attributes.body
            } catch (e: LBKtorException) {
                e.message ?: "error"
            } catch (e: RemoteDogException) {
                buildString {
                    appendLine(e.message ?: "error")
                    e.body?.let(::appendLine)
                }
            }
        }
    }

    @Serializable
    data class ApiDogFact(
        val data: List<ApiDogFactData>,
    )

    @Serializable
    data class ApiDogFactData(
        val attributes: ApiDogFactAttributes,
    )

    @Serializable
    data class ApiDogFactAttributes(
        val body: String,
    )

    @Serializable
    data class ApiDogFactUnexpected(
        val data: List<ApiDogFactData>,
        val nonExistingFieldsButMandatory: String,
    )
}

@Suppress("UNCHECKED_CAST")
class KtorDemoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KtorDemoViewModel() as T
    }
}
