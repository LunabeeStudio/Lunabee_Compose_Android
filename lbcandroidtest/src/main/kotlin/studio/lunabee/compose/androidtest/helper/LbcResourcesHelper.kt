package studio.lunabee.compose.androidtest.helper

import android.content.Context
import java.io.File

@Suppress("NOTHING_TO_INLINE", "UNUSED")
object LbcResourcesHelper {
    /**
     * Get content of a test resource file as [String].
     * Let assume that your file "myFile.txt" is located in myModule/src/main/resources:
     * ```
     * val fileContentAsString = LbcResourcesHelper.readResourceAsString("myFile.txt")
     * ```
     *
     * @param pathResource exact path resource name (ex: "dev/myFile.txt"). This param does not accept folder.
     */
    inline fun readResourceAsString(pathResource: String): String =
        javaClass.classLoader!!.getResourceAsStream(pathResource).use {
            it.bufferedReader().readText()
        }

    /**
     * Get content of a test resource file as [ByteArray].
     * Let assume that your file "myFile.txt" is located in myModule/src/main/resources:
     * ```
     * val fileContentAsByteArray = LbcResourcesHelper.readResourceAsBytes("myFile.txt")
     * ```
     *
     * @param pathResource exact path resource name (ex: "dev/myFile.txt"). This param does not accept folder.
     */
    inline fun readResourceAsBytes(pathResource: String): ByteArray =
        javaClass.classLoader!!.getResourceAsStream(pathResource).use {
            it.readBytes()
        }

    /**
     * Copy resource content to a specific file in the tested device.
     * This method does not accept folder as resource. See [copyFolderResourceToDeviceFile] for this usage.
     *
     * Let assume that your file "myFile.txt" is located in myModule/src/main/resources:
     * ```
     * val destinationFile = File(cacheDir, "myFile.txt")
     * LbcResourcesHelper.copyResourceToDeviceFile("myFile.txt", destinationFile)
     * destinationFile.exists() // should be true
     * ```
     *
     * @param pathResource exact path resource name (ex: "dev/myFile.txt"). This param does not accept folder.
     * @param deviceDestinationFile location to store the resource file in the device.
     */
    inline fun copyResourceToDeviceFile(pathResource: String, deviceDestinationFile: File) {
        javaClass.classLoader!!.getResourceAsStream(pathResource).use { inputStream ->
            deviceDestinationFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    /**
     * Copy a folder resource content to a specific file in the tested device.
     * If [deviceDestinationFile] does not exist, it will be automatically created.
     *
     * Let assume you have a tree structure like this, located in myModule/src/main/resources:
     * If you have a folder structure like this:
     * - myParentFolder
     * -- myFile
     * -- myChildFolder
     * --- myChildFile
     *
     * ```
     * val parentFolder = LbcFolderResource("myParentFolder", true, null)
     * val myFile = LbcFolderResource("myFile", false, parentFolder)
     * val childFolder = LbcFolderResource("myChildFolder", true, parentFolder)
     * val myChildFile = LbcFolderResource("myChildFile", false, childFolder)
     * val destinationFile = File(cacheDir, "myTestFolderOnDevice")
     * LbcResourcesHelper.copyFolderResourceToDeviceFile(listOf(parentFolder, myFile, childFolder, myChildFile)), destinationFile, context)
     * destinationFile.listFiles() // should represent your tree structure
     * ```
     *
     * @param folderResources describe the tree structure of your folder.
     * @param deviceDestinationFile location to store the resource file in the device.
     * @param context
     * @param overrideExistingFolder delete potential existing folder if already exists before
     */
    inline fun copyFolderResourceToDeviceFile(
        folderResources: List<LbcFolderResource>,
        deviceDestinationFile: File,
        context: Context,
        overrideExistingFolder: Boolean = true
    ) {
        if (overrideExistingFolder) {
            deviceDestinationFile.deleteRecursively()
        }

        if (!deviceDestinationFile.exists()) {
            deviceDestinationFile.mkdirs()
        }

        folderResources.forEach { folderResource ->
            if (folderResource.isDirectory) {
                val newFile = File(context.cacheDir, folderResource.pathResourceName)
                newFile.mkdirs()
            } else {
                javaClass.classLoader!!.getResourceAsStream(folderResource.pathResourceName).use {
                    inputStream
                    ->
                    File(context.cacheDir, folderResource.pathResourceName).outputStream().use {
                        outputStream
                        ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }
        }
    }
}
