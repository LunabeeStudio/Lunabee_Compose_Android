package studio.lunabee.compose.androidtest.extension

import android.os.Build
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import studio.lunabee.compose.androidtest.rule.LbcPrintRule

/**
 * Print the composable to the screenshot dir. Does nothing below API 26.
 * ```
 *      onNodeWithTag("MyTag")
 *          .printToCacheDir(printRule, "_suffix")
 * ```
 */
fun SemanticsNodeInteraction.printToCacheDir(
    printRule: LbcPrintRule,
    suffix: String = "",
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        printRule.print(captureToImage().asAndroidBitmap(), suffix)
    }
}
