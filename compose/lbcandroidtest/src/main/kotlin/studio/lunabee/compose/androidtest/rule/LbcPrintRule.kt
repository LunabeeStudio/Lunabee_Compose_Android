package studio.lunabee.compose.androidtest.rule

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.test.core.app.takeScreenshot
import androidx.test.core.app.takeScreenshotNoSync
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import studio.lunabee.compose.androidtest.rule.LbcPrintRule.Companion.internalStorage
import studio.lunabee.compose.androidtest.rule.LbcPrintRule.Companion.publicStorage
import java.io.File

/**
 * Custom rule to print a screenshot from a test
 * Use [internalStorage] or [publicStorage] for default constructions
 * When using internal storage, make sure to set `android.injected.androidTest.leaveApksInstalledAfterRun=true` in the project
 * `gradle.property` file to keep screenshots after test run
 *
 * @param appName The application name used as a subfolder of the screenshot directory
 * @param usePublicStorage Whereas screenshots will be stored in public or internal storage
 * @param deleteOnSuccess If true, only keeps screenshots of failing tests
 * @param appendTimestamp Append the test start time to the screenshot subfolder
 */
class LbcPrintRule(
    private val appName: String?,
    private val usePublicStorage: Boolean,
    private val deleteOnSuccess: Boolean,
    private val appendTimestamp: Boolean = usePublicStorage,
    private val verbose: Boolean,
) : TestWatcher() {

    companion object {
        /**
         * Factory for internal storage print rule
         *
         * @see [LbcPrintRule]
         */
        fun internalStorage(
            appName: String? = null,
            usePublicStorage: Boolean = false,
            deleteOnSuccess: Boolean = true,
            appendTimestamp: Boolean = usePublicStorage,
            verbose: Boolean = true,
        ): LbcPrintRule = LbcPrintRule(
            appName = appName,
            usePublicStorage = usePublicStorage,
            deleteOnSuccess = deleteOnSuccess,
            appendTimestamp = appendTimestamp,
            verbose = verbose,
        )

        /**
         * Factory for public storage print rule
         *
         * @see [LbcPrintRule]
         */
        fun publicStorage(
            appName: String = InstrumentationRegistry.getInstrumentation().targetContext.packageName,
            usePublicStorage: Boolean = true,
            deleteOnSuccess: Boolean = true,
            appendTimestamp: Boolean = usePublicStorage,
            verbose: Boolean = true,
        ): LbcPrintRule = LbcPrintRule(
            appName = appName,
            usePublicStorage = usePublicStorage,
            deleteOnSuccess = deleteOnSuccess,
            appendTimestamp = appendTimestamp,
            verbose = verbose,
        )
    }

    private lateinit var appFolder: File
    private lateinit var classFolder: File

    private lateinit var screenshotFolder: File

    lateinit var basePath: String
        private set

    private val screenshots: MutableList<File> = mutableListOf()

    private var counter = 0

    override fun starting(d: Description) {
        super.starting(d)
        counter = 0

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        screenshotFolder = if (usePublicStorage) {
            val publicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            File(publicDirectory, "lbc_screenshots")
        } else {
            File(context.cacheDir, "screenshot")
        }

        val subFolder = StringBuilder(appName.orEmpty())
        if (appendTimestamp) {
            if (subFolder.isNotEmpty()) {
                subFolder.append("_")
            }
            subFolder.append(System.currentTimeMillis())
        }
        appFolder = if (subFolder.isEmpty()) {
            screenshotFolder
        } else {
            File(screenshotFolder, subFolder.toString())
        }

        val className = d.className.substringAfterLast(".")
        classFolder = File(appFolder, className)
        basePath = File(classFolder, d.methodName).absolutePath

        if (verbose) {
            Log.v("LbcPrintRule", "Screenshot test path: $basePath")
        }
    }

    fun print(bitmap: Bitmap, suffix: String) {
        try {
            val screenFile = File("${basePath}_${counter++}$suffix.jpeg")
            if (screenFile.exists()) screenFile.delete()
            screenFile.parentFile?.mkdirs()
            screenFile.outputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            }

            if (verbose) {
                Log.v("LbcPrintRule", "Screenshot saved to ${screenFile.absolutePath}")
            }

            screenshots += screenFile
        } finally {
            bitmap.recycle()
        }
    }

    @SuppressLint("RestrictedApi")
    fun printWholeScreen(suffix: String, noSync: Boolean = false) {
        val bitmap = try {
            if (noSync) {
                takeScreenshotNoSync()
            } else {
                takeScreenshot()
            }
        } catch (e: Throwable) {
            Log.e("LbcPrintRule", "screenshot failed", e)
            null
        }

        bitmap?.let { print(it, suffix) }
    }

    fun getScreenshotDir(): File {
        screenshotFolder.mkdirs()
        return screenshotFolder
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        if (deleteOnSuccess) {
            if (verbose && screenshots.isNotEmpty()) {
                Log.v("LbcPrintRule", "Delete screenshots\n${screenshots.joinToString("\n") { "\t$it" }}")
            }
            screenshots.forEach(File::delete)
            classFolder.delete() // try delete (fail if not empty)
            appFolder.delete() // try delete (fail if not empty)
        }
    }
}
