/*
 * Copyright (c) 2024 Lunabee Studio
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
 * Main.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 11/19/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.jetbrains.compose.resources.ExperimentalResourceApi
import studio.lunabee.compose.core.kmp.LbcTextSpec
import studio.lunabee.compose.webapp.generated.resources.Res
import studio.lunabee.compose.webapp.generated.resources.allStringResources
import studio.lunabee.compose.webapp.generated.resources.fallback
import studio.lunabee.compose.webapp.generated.resources.test
import studio.lunabee.compose.webapp.generated.resources.test_args
import studio.lunabee.compose.webapp.generated.resources.test_args_plural
import studio.lunabee.compose.webapp.generated.resources.test_other_args

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        Column {
            Text(
                "Hello: ${LbcTextSpec.StringResource(Res.string.test).string}",
            )
            Text(
                "Test with string params: ${
                    LbcTextSpec.StringResource(
                        Res.string.test_other_args,
                        "foo",
                        123,
                    ).string
                }",
            )
            Text(
                "Test with recursive string params: ${
                    LbcTextSpec.StringResource(
                        Res.string.test_args,
                        LbcTextSpec.StringResource(Res.string.test),
                        123,
                    ).string
                }",
            )
            Text(
                "Test plural: ${
                    LbcTextSpec.PluralsResource(
                        Res.plurals.test_args_plural,
                        1,
                        1,
                    ).string
                } && ${
                    LbcTextSpec.PluralsResource(
                        Res.plurals.test_args_plural,
                        2,
                        2,
                    ).string
                }",
            )
            Text(
                "Test by name: ${
                    LbcTextSpec.StringByNameResource(
                        key = "test",
                        fallbackResource = Res.string.fallback,
                        allStringResources = Res.allStringResources,
                    ).string
                } && ${
                    LbcTextSpec.StringByNameResource(
                        key = "test_args",
                        allStringResources = Res.allStringResources,
                        fallbackResource = Res.string.fallback,
                        "foo",
                        123,
                    ).string
                } && ${
                    LbcTextSpec.StringByNameResource(
                        key = "does_not_exist",
                        allStringResources = Res.allStringResources,
                        fallbackResource = Res.string.fallback,
                        "foo",
                        123,
                    ).string
                }",
            )
        }
    }
}
