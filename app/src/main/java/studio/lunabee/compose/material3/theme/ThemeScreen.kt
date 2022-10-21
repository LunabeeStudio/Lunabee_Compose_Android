/*
 * Copyright © 2022 Lunabee Studio
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
 *
 * ThemeScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 10/21/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material3.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import studio.lunabee.compose.R
import studio.lunabee.compose.lbctheme.LbcThemeUtilities
import studio.lunabee.compose.material3.LunabeeComposeMaterial3Theme
import studio.lunabee.compose.navigation.ToDirection
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeScreen(
    systemUiController: SystemUiController,
    navigateBack: ToDirection,
) {
    var colorHex: String by rememberSaveable { mutableStateOf(value = "") }
    val customColor = try {
        Color(android.graphics.Color.parseColor("#${colorHex.replace("#", "")}"))
    } catch (e: Exception) {
        null
    }

    LunabeeComposeMaterial3Theme(
        systemUiController = systemUiController,
        customColorScheme = customColor?.let {
            LbcThemeUtilities.getMaterialColorSchemeFromColor(it, isInDarkMode = isSystemInDarkTheme())
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.material3_theme_title)) },
                    navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues = it),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                ) {
                    OutlinedTextField(
                        value = colorHex,
                        onValueChange = { colorHex = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        label = { Text(text = stringResource(id = R.string.material3_theme_color_label)) },
                        placeholder = { Text(text = stringResource(id = R.string.material3_theme_color_placeholder)) },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    BoxWithColorHex(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(
                        items = ColorScheme::class.java.declaredFields,
                    ) { field ->
                        field.isAccessible = true
                        @Suppress("unchecked_cast")
                        val color = (field.get(MaterialTheme.colorScheme) as? State<Color>)?.value

                        val colorName = field.name.substringBefore('$')
                        Text(
                            text = colorName,
                            modifier = Modifier
                                .padding(all = 16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )

                        color?.let {
                            LazyRow {
                                item {
                                    BoxWithColorHex(
                                        color = color,
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(all = 8.dp)
                                    )
                                }

                                for (i in 5 until 100 step 5) {
                                    item {
                                        val tone = LbcThemeUtilities.getToneForColor(color = color, tone = i)

                                        BoxWithColorHex(
                                            color = tone,
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .padding(all = 8.dp)
                                        ) {
                                            Text(
                                                text = (100 - i).toString(),
                                                color = if (tone.luminance() >= 0.5) Color.Black else Color.White,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxWithColorHex(
    color: Color,
    modifier: Modifier = Modifier,
    innerText: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = RoundedCornerShape(size = 4.dp))
                .drawBehind { drawRect(color) },
            contentAlignment = Alignment.Center
        ) {
            innerText?.invoke()
        }

        Text(
            text = color.hexValue,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

val Color.hexValue: String
    get() = String.format(locale = Locale.getDefault(), format = "#%08X", toArgb())
