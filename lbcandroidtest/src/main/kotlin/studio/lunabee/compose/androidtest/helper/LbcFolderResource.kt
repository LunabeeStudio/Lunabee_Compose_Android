/*
 * Copyright © 2023 Lunabee Studio
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
 * LbcFolderResource.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 2/20/2023 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.androidtest.helper

import java.io.File

/**
 * Describe your folder tree with this class.
 * If you have a folder structure like this:
 * - myParentFolder
 * -- myFile
 * -- myChildFolder
 * --- myChildFile
 *
 * Your LbcFolderResource can be declared like this:
 * ```
 *      val parentFolder = LbcFolderResource("myParentFolder", true, null)
 *      val myFile = LbcFolderResource("myFile", false, parentFolder)
 *      val childFolder = LbcFolderResource("myChildFolder", true, parentFolder)
 *      val myChildFile = LbcFolderResource("myChildFile", false, childFolder)
 * ```
 * Then you can use it with [LbcResourcesHelper.copyFolderResourceToDeviceFile].
 */
data class LbcFolderResource(
    val resourceName: String,
    val isDirectory: Boolean,
    val parentResource: LbcFolderResource?
) {
    val pathResourceName: String
        get() =
            if (parentResource ==
                null
            ) {
                resourceName
            } else {
                parentResource.pathResourceName + File.separator + resourceName
            }
}
