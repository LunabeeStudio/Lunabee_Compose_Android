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
 * MenuBackScreen.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 4/8/2022 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.material.common.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import studio.lunabee.compose.R
import studio.lunabee.compose.material.common.section.MenuSection
import studio.lunabee.compose.model.MenuEntry
import studio.lunabee.compose.navigation.ToDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBack3Screen(
    title: String,
    menus: List<MenuEntry>,
    navigateToPreviousScreen: ToDirection,
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = navigateToPreviousScreen) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it),
        ) {
            MenuSection(
                lazyListState = lazyListState,
                menus = menus,
            )
        }
    }
}
