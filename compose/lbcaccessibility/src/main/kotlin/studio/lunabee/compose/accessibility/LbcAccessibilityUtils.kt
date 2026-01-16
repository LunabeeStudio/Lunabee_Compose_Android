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

package studio.lunabee.compose.accessibility

object LbcAccessibilityUtils {
    /**
     * Remove emoji or specific characters for accessibility that are not granted by [Regex].
     * See https://stackoverflow.com/a/49516025/10935947
     *
     * @return lean string
     */
    fun String.cleanForAccessibility(): String {
        val characterFilter = Regex("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\p{Sc}\\s]")
        return replace(regex = characterFilter, replacement = "")
    }
}
