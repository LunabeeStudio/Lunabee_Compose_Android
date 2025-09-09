package studio.lunabee.compose.demo

import androidx.compose.material3.Text
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import studio.lunabee.compose.androidtest.LbcComposeTest
import studio.lunabee.compose.androidtest.extension.waitAndPrintRootToCacheDir
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalTestApi::class)
class PrintRuleDemoTest : LbcComposeTest() {

    @Test
    fun print_screenshot_on_timeout_test(): Unit = invoke {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        setContent {
            Text("foo")
        }

        val error = runCatching {
            hasText("bar")
                .waitAndPrintRootToCacheDir(printRule, timeout = 200.milliseconds)
                .assertIsDisplayed()
        }.exceptionOrNull()
        assertIs<ComposeTimeoutException>(error)

        val base = File(printRule.basePath)
        val parentFile = base.parentFile!!
        assertTrue(parentFile.exists())

        val files = parentFile.listFiles()!!
        assertEquals(1, files.size)
        assertEquals(
            File(context.cacheDir, "screenshot/PrintRuleDemoTest/print_screenshot_on_timeout_test_0_TIMEOUT.jpeg")
                .absolutePath,
            files.first().absolutePath,
        )

        // assert(false) // Make the test fail to check if screenshots still exist in device cache storage
    }

    // /**
    //  * Screenshot on failure testing (manual testing)
    //  */
    //    @Test
    //    fun print_screenshot_on_failure_test(): Unit = invoke {
    //        setContent {
    //            Box(
    //                modifier = Modifier
    //                    .fillMaxSize()
    //                    .background(Brush.verticalGradient(listOf(Color.Red, Color.Blue))),
    //            ) {
    //                Text(text = "my text")
    //            }
    //        }
    //
    //        hasText("not my text")
    //            .waitUntilExactlyOneExists()
    //            .assertIsDisplayed()
    //    }

    @Test
    fun getScreenshotDir_exists_test(): Unit = invoke {
        val dir = printRule.getScreenshotDir()
        assertTrue(dir.exists())
    }
}
