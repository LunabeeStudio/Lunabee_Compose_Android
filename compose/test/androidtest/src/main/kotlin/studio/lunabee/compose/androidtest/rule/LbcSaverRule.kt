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

package studio.lunabee.compose.androidtest.rule

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule

/**
 * Custom rule to execute a test with a [StateRestorationTester]. Instead of calling [ComposeContentTestRule.setContent] you need
 * to use [StateRestorationTester.setContent]. You can use the [ComposeContentTestRule] to assert your state is restored properly.
 * Example:
 * ```
 *     @get:Rule(order = 0)
 *     val saverRule: LbcSaverRule = LbcSaverRule()
 *
 *     @Test
 *     fun my_test() {
 *         saverRule(
 *             content = {
 *                 var value by rememberSaveable { mutableStateOf("") }
 *                 TextField(value = value, onValueChange = { value = it }, modifier = Modifier.testTag("tag"))
 *             },
 *         ) {
 *             onNodeWithTag("tag").assertTextContains("")
 *             onNodeWithTag("tag").performTextReplacement("test")
 *             saverRule.emulateSavedInstanceStateRestore()
 *             onNodeWithTag("tag").assertTextContains("test")
 *         }
 *     }
 * ```
 */
@Suppress("UNUSED")
class LbcSaverRule : ComposeContentTestRule by createAndroidComposeRule<ComponentActivity>() {
    private val stateRestoration: StateRestorationTester by lazy {
        StateRestorationTester(composeTestRule = this)
    }

    operator fun invoke(
        content: @Composable () -> Unit,
        block: ComposeContentTestRule.() -> Unit,
    ) {
        stateRestoration.setContent(content)
        block(this)
    }

    fun emulateSavedInstanceStateRestore() {
        stateRestoration.emulateSavedInstanceStateRestore()
    }
}
