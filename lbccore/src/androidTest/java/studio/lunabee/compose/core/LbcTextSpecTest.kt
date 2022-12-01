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
 * LbcTextSpecTest.kt
 * Lunabee Compose
 *
 * Created by Lunabee Studio / Date - 12/01/2022 - for the Lunabee Compose library.
 */
package studio.lunabee.compose.core

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.AnnotatedString
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class LbcTextSpecTest {
    @get:Rule
    val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity> =
        createAndroidComposeRule()

    @Test
    fun raw_test() {
        val expected = "test"
        val textSpec = LbcTextSpec.Raw(value = expected)
        composeTestRule.setContent {
            assertEquals(expected, textSpec.string)
            assertEquals(AnnotatedString(expected), textSpec.annotated)
        }
    }

    @Test
    fun raw_args_test() {
        val param = "param"
        val test = "test %s"
        val expected = test.format(param)
        val textSpec = LbcTextSpec.Raw(value = test, param)
        composeTestRule.setContent {
            assertEquals(expected, textSpec.string)
            assertEquals(AnnotatedString(expected), textSpec.annotated)
        }
    }

    @Test
    fun annotated_test() {
        val expected = AnnotatedString("test")
        val textSpec = LbcTextSpec.Annotated(value = expected)
        composeTestRule.setContent {
            assertEquals(expected, textSpec.annotated)
            assertEquals(expected.text, textSpec.string)
        }
    }

    @Test
    fun stringRes_test() {
        val expectedTest = composeTestRule.activity.getString(studio.lunabee.compose.core.test.R.string.test)
        val actualTest = LbcTextSpec.StringResource(id = studio.lunabee.compose.core.test.R.string.test)

        val stringParam = "foo"
        val intParam = 123
        val expectedTestArgs = composeTestRule
            .activity
            .getString(studio.lunabee.compose.core.test.R.string.test_args, stringParam, intParam)
        val actualTestArgs = LbcTextSpec.StringResource(id = studio.lunabee.compose.core.test.R.string.test_args, stringParam, intParam)

        composeTestRule.setContent {
            assertEquals(expectedTest, actualTest.string)
            assertEquals(expectedTestArgs, actualTestArgs.string)

            assertEquals(AnnotatedString(expectedTest), actualTest.annotated)
            assertEquals(AnnotatedString(expectedTestArgs), actualTestArgs.annotated)
        }
    }

    @Test
    fun stringRes_recursive_test() {
        val intParam = 123
        val stringParam = composeTestRule.activity.getString(studio.lunabee.compose.core.test.R.string.test)
        val stringParamTextSpec = LbcTextSpec.StringResource(id = studio.lunabee.compose.core.test.R.string.test)

        val expectedTestArgs = composeTestRule.activity.getString(
            studio.lunabee.compose.core.test.R.string.test_args,
            stringParam,
            intParam,
        )
        val actualTestArgs = LbcTextSpec.StringResource(studio.lunabee.compose.core.test.R.string.test_args, stringParamTextSpec, intParam)

        composeTestRule.setContent {
            assertEquals(expectedTestArgs, actualTestArgs.string)
            assertEquals(AnnotatedString(expectedTestArgs), actualTestArgs.annotated)
        }
    }

    @Test
    fun pluralsRes_test() {
        val expectedTestOne = composeTestRule
            .activity
            .resources
            .getQuantityString(studio.lunabee.compose.core.test.R.plurals.test_args_plural, 1, 1)
        val actualTestOne = LbcTextSpec.PluralsResource(studio.lunabee.compose.core.test.R.plurals.test_args_plural, 1, 1)

        val expectedTestMany = composeTestRule
            .activity
            .resources
            .getQuantityString(studio.lunabee.compose.core.test.R.plurals.test_args_plural, 2, 2)
        val actualTestMany = LbcTextSpec.PluralsResource(studio.lunabee.compose.core.test.R.plurals.test_args_plural, 2, 2)

        composeTestRule.setContent {
            assertEquals(expectedTestOne, actualTestOne.string)
            assertEquals(expectedTestMany, actualTestMany.string)

            assertEquals(AnnotatedString(expectedTestOne), actualTestOne.annotated)
            assertEquals(AnnotatedString(expectedTestMany), actualTestMany.annotated)
        }
    }
}
