/*
 * Copyright (c) 2024 Lunabee Studio
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
 * GlanceStringResources.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.helpers

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext

/**
 * As the default stringResources method is not supported in a Glance context,
 * use this one to avoid using [LocalContext] directly each time.
 */
@Suppress("unused")
@Composable
fun glanceStringResource(@StringRes id: Int, vararg args: Any): String = LocalContext.current.getString(id, *args)

/**
 * As the default pluralResource method is not supported in a Glance context,
 * use this one to avoid using [LocalContext] directly each time.
 */
@Suppress("unused")
@Composable
fun glancePluralResource(@PluralsRes id: Int, quantity: Int, vararg args: Any): String =
    LocalContext.current.resources.getQuantityString(id, quantity, *args)