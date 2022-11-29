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
 * TextScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 7/29/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.text

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import studio.lunabee.compose.R
import studio.lunabee.compose.lbcmaterial.StyledTextItem
import studio.lunabee.compose.lbcmaterial.model.TextToHighlight
import studio.lunabee.compose.lbctopappbar.material.LbcTopAppBar
import studio.lunabee.compose.lbctopappbar.material.model.LbcTopAppBarAction
import java.util.UUID

@OptIn(ExperimentalTextApi::class)
@Composable
fun TextScreen(
    navigateToPreviousScreen: () -> Unit,
) {
    var rawText: String by rememberSaveable { mutableStateOf(value = "") }
    var highlightedText: String by rememberSaveable { mutableStateOf(value = "") }

    Scaffold(
        topBar = {
            LbcTopAppBar(
                title = stringResource(id = R.string.text_screen_title),
                navigationAction = LbcTopAppBarAction.DrawableResAction(
                    iconRes = R.drawable.ic_back,
                    contentDescription = null,
                    action = navigateToPreviousScreen,
                ),
                applyStatusBarPadding = true,
                topAppBarBackgroundColor = MaterialTheme.colors.surface,
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            ) {
                OutlinedTextField(
                    value = rawText,
                    onValueChange = { rawText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.text_screen_base_text_placeholder))
                    },
                )

                OutlinedTextField(
                    value = highlightedText,
                    onValueChange = { highlightedText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.text_screen_highlight_placeholder))
                    },
                )

                StyledTextItem(
                    rawBaseText = rawText,
                    rawBaseTextStyle = MaterialTheme.typography.body1,
                    textToHighLightList = getTextToHighlightWithStyle(
                        highlightedText = highlightedText,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold).toSpanStyle(),
                        context = LocalContext.current,
                    ),
                )

                StyledTextItem(
                    rawBaseText = rawText,
                    rawBaseTextStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                    textToHighLightList = getTextToHighlightWithStyle(
                        highlightedText = highlightedText,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                        ).toSpanStyle(),
                        context = LocalContext.current,
                    ),
                )

                StyledTextItem(
                    rawBaseText = rawText,
                    rawBaseTextStyle = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
                    textToHighLightList = getTextToHighlightWithStyle(
                        highlightedText = highlightedText,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            textDecoration = TextDecoration.LineThrough,
                        ).toSpanStyle(),
                        context = LocalContext.current,
                        ignoreCase = true,
                    ),
                )

                StyledTextItem(
                    rawBaseText = stringResource(id = R.string.lorem_ipsum),
                    rawBaseTextStyle = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Center),
                    textToHighLightList = getTextToHighlightWithStyle(
                        highlightedText = stringResource(id = R.string.highlighted_lorem_ipsum),
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.combine(listOf(TextDecoration.Underline, TextDecoration.LineThrough)),
                        ).toSpanStyle()
                            .copy(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF9400D3),
                                        Color(0xFF4B0082),
                                        Color(0xFF0000FF),
                                        Color(0xFF00FF00),
                                        Color(0xFFFFFF00),
                                        Color(0xFFFF7F00),
                                        Color(0xFFFF0000),
                                    ),
                                ),
                            ),
                        context = LocalContext.current,
                        ignoreCase = true,
                    ),
                )
            }
        }
    }
}

private fun getTextToHighlightWithStyle(
    highlightedText: String,
    style: SpanStyle,
    context: Context,
    ignoreCase: Boolean = false,
): Set<TextToHighlight> {
    return highlightedText.takeIf { it.isNotEmpty() }?.split(",")?.mapNotNull {
        if (it.isNotEmpty()) {
            TextToHighlight(
                tag = UUID.randomUUID().toString(),
                text = it,
                style = style,
                action = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                ignoreCase = ignoreCase,
            )
        } else {
            null
        }
    }?.toSet() ?: emptySet()
}
