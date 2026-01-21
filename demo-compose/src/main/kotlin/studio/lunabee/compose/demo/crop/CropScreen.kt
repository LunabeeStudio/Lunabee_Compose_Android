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

package studio.lunabee.compose.demo.crop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import studio.lunabee.compose.R
import studio.lunabee.compose.crop.CropImageSize
import studio.lunabee.compose.crop.LbCropView
import studio.lunabee.compose.crop.rememberLbCropViewState

@Composable
fun CropScreen() {
    val context = LocalContext.current
    var bitmap: Bitmap by remember { mutableStateOf(BitmapFactory.decodeResource(context.resources, R.drawable.oss117)) }
    var showCropView: Boolean by rememberSaveable { mutableStateOf(true) }
    val state = rememberLbCropViewState(
        originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.oss117),
        originalOrientation = 1,
        finalImageMinSize = CropImageSize(
            width = 500,
            height = 500,
        ),
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showCropView) {
            Text(
                text = stringResource(id = R.string.crop_screen_description),
                modifier = Modifier.padding(16.dp),
            )
            LbCropView(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f),
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        val newBitmap = state.crop()
                        bitmap = newBitmap
                        showCropView = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = stringResource(id = R.string.crop_screen_crop_button))
            }
        } else {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
            )

            Button(
                onClick = {
                    showCropView = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = stringResource(id = R.string.crop_screen_retry_button))
            }
        }
    }
}
