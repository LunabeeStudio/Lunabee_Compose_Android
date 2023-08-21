package studio.lunabee.compose.demo

import androidx.compose.material3.Text
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
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
    fun print_screenshot_on_failure_test(): Unit = invoke {
        setContent {
            Text("foo")
        }

        val error = runCatching {
            hasText("bar")
                .waitAndPrintRootToCacheDir(this, printRule, timeout = 200.milliseconds)
                .assertIsDisplayed()
        }.exceptionOrNull()
        assertIs<ComposeTimeoutException>(error)

        val base = File(printRule.basePath)
        val parentFile = base.parentFile!!
        assertTrue(parentFile.exists())

        val files = parentFile.listFiles()!!
        assertEquals(1, files.size)
        assertEquals(
            "/storage/emulated/0/Download/lbc_screenshots/studio.lunabee.compose/PrintRuleDemoTest/print_screenshot_on_failure_test_0_TIMEOUT.jpeg",
            files.first().absolutePath
        )

        // assert(false) // Make the test fail to check if screenshots still exist in device cache storage
    }
}
