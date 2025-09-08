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

    operator fun invoke(content: @Composable () -> Unit, block: ComposeContentTestRule.() -> Unit) {
        stateRestoration.setContent(content)
        block(this)
    }

    fun emulateSavedInstanceStateRestore() {
        stateRestoration.emulateSavedInstanceStateRestore()
    }
}
