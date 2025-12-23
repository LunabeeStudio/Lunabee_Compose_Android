/*
 * Copyright (c) 2025 Lunabee Studio
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
 * LbcInjectComponentActivityRule.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/22/2025 - for the Lunabee Compose library.
 */

package studio.lunabee.compose.robolectrictest

import android.app.Application
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.robolectric.Shadows.shadowOf
import kotlin.jvm.java

class LbcInjectComponentActivityRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        val appContext: Application = ApplicationProvider.getApplicationContext()
        val activityInfo = ActivityInfo().apply {
            name = ComponentActivity::class.java.name
            packageName = appContext.packageName
        }
        shadowOf(appContext.packageManager).addOrUpdateActivity(activityInfo)
    }
}
