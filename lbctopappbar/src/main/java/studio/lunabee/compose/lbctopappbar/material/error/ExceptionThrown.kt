/*
 * Copyright © 2022 Lunabee Studio
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
 * ExceptionThrown.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.lbctopappbar.material.error

internal object ExceptionThrown {
    internal object LbcTopAppBarNoTitleException : IllegalArgumentException(
        "No title to display.\n" +
            "Either specify a title or provide your own titleComposable implementation"
    )

    internal object LbcTopAppBarNoNavigationException : IllegalArgumentException(
        "You either need to:\n" +
            "- Provide onNavigationClicked action\n" +
            "- Provide your implementation of navigationIconComposable\n" +
            "- Use LbcTopAppBar directly"
    )
}
