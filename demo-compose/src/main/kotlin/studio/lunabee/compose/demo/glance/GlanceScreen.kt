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

package studio.lunabee.compose.demo.glance

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.lunabee.compose.glance.helpers.PinWidgetToHomeScreenHelper

@Composable
fun GlanceScreen() {
    val context: Context = LocalContext.current
    val pinWidgetHelper: PinWidgetToHomeScreenHelper = remember {
        PinWidgetToHomeScreenHelper(context)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
    ) {
        if (pinWidgetHelper.isPinSupported()) {
            // Used for demo purpose directly in the Composable.
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutineScope.launch {
                        pinWidgetHelper.pin(GlanceWidgetDemo::class.java, GlanceWidgetDemoReceiver::class.java)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(text = "Pin the widget!")
            }
        } else {
            Text(text = "Pin widget is not supported")
        }
    }
}
