package studio.lunabee.compose.androidtest

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.ui.test.AndroidComposeUiTest
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runAndroidComposeUiTest
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import studio.lunabee.compose.androidtest.rule.LbcPrintRule
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * This class abstracts some logic to run a test inside [ComposeUiTest]
 * Example:
 * ```
 * class MyComposeTest: LbcComposeTest() {
 *      @Test
 *      fun my_screen_test() {
 *          invoke {
 *              hasTestTag(getString(R.string.my_string_res))
 *                  .waitUntilOnlyOneExists(this) // not mandatory, you can also use directly onNodeWithTag if you don't need to wait.
 *                  .assertIsDisplayed()
 *          }
 *      }
 *
 *       @Test
 *      fun my_screen_test() {
 *          invoke(clazz = ComponentActivity::class.java) { activity ->l
 *              hasTestTag(getString(R.string.my_string_res))
 *                  .waitUntilOnlyOneExists(this) // not mandatory, you can also use directly onNodeWithTag if you don't need to wait.
 *                  .assertIsDisplayed()
 *
 *              val insets = WindowInsetsCompat.toWindowInsetsCompat(activity.window.decorView.rootWindowInse
 *              // Do additional operations
 *          }
 *      }
 * }
 * ```
 */
@Suppress("UNUSED", "UnnecessaryAbstractClass")
@OptIn(ExperimentalTestApi::class)
abstract class LbcComposeTest {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val printRule: LbcPrintRule = LbcPrintRule.internalStorage()

    fun getString(@StringRes id: Int, vararg args: Any): String = context.getString(id, *args)

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg args: Any): String =
        context.resources.getQuantityString(id, quantity, *args)

    /**
     * Run a test in a [ComposeUiTest] with a default [androidx.activity.ComponentActivity].
     */
    operator fun invoke(
        effectContext: CoroutineContext = EmptyCoroutineContext,
        block: ComposeUiTest.() -> Unit,
    ) {
        runComposeUiTest(effectContext = effectContext) {
            try {
                block()
            } catch (e: Throwable) {
                val suffix = LbcAndroidTestConstants.FailureSuffix + "_${e.javaClass.simpleName}"
                printRule.printWholeScreen(suffix)
                throw e
            }
        }
    }

    /**
     * Run a test in a [ComposeUiTest] with activity [clazz] you want.
     * You will be able to use the activity in your [block]. [invoke] without [clazz] should be preferred
     * if you don't need an activity in your [block].
     */
    inline operator fun <reified A : ComponentActivity> invoke(
        clazz: Class<A>,
        effectContext: CoroutineContext = EmptyCoroutineContext,
        noinline block: AndroidComposeUiTest<A>.() -> Unit,
    ) {
        runAndroidComposeUiTest(
            activityClass = clazz,
            effectContext = effectContext,
        ) {
            try {
                block()
            } catch (e: Throwable) {
                val suffix = LbcAndroidTestConstants.FailureSuffix + "_${e.javaClass.simpleName}"
                printRule.printWholeScreen(suffix)
                throw e
            }
        }
    }
}
