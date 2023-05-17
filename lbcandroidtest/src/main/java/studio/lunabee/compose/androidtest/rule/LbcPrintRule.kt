package studio.lunabee.compose.androidtest.rule

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.test.core.app.takeScreenshot
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

/**
 * Custom rule to print a screenshot from a test.
 */
class LbcPrintRule(private val context: Context) : TestWatcher() {

    private lateinit var classFolder: File

    lateinit var basePath: String
        private set

    private val screenshots: MutableList<File> = mutableListOf()

    private var counter = 0

    override fun starting(d: Description) {
        super.starting(d)
        counter = 0
        val screenshotFolder = File(context.cacheDir, "screenshot")
        val className = d.className.substringAfterLast(".")
        classFolder = File(screenshotFolder, className)
        basePath = File(classFolder, d.methodName).absolutePath
    }

    fun print(bitmap: Bitmap, suffix: String) {
        try {
            val screenFile = File("${basePath}_${counter++}$suffix.jpeg")
            screenFile.parentFile?.mkdirs()
            screenFile.outputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            }
            screenshots += screenFile
        } finally {
            bitmap.recycle()
        }
    }

    fun printWholeScreen(suffix: String) {
        val bitmap = try {
            takeScreenshot()
        } catch (e: Throwable) {
            Log.e("LbcPrintRule", "screenshot failed", e)
            null
        }

        bitmap?.let { print(it, suffix) }
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        screenshots.forEach(File::delete)
        classFolder.delete() // try delete (fail if not empty)
    }
}
