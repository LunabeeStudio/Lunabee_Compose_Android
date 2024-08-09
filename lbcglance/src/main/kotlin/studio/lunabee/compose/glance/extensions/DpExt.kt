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
 * DpExt.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 8/9/2024 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.glance.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.glance.GlanceModifier
import androidx.glance.layout.ColumnScope
import androidx.glance.layout.RowScope
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.width

@Suppress("unused")
@Composable
fun Dp.VerticalSpacer(): Unit = Spacer(modifier = GlanceModifier.height(this))

@Suppress("unused")
@Composable
fun Dp.HorizontalSpacer(): Unit = Spacer(modifier = GlanceModifier.width(this))

@Suppress("unused")
@Composable
fun ColumnScope.WeightSpacer(): Unit = Spacer(modifier = GlanceModifier.defaultWeight())

@Suppress("unused")
@Composable
fun RowScope.WeightSpacer(): Unit = Spacer(modifier = GlanceModifier.defaultWeight())
