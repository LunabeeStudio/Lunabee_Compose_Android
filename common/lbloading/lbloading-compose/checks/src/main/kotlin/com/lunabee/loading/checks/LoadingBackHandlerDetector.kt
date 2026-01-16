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

package com.lunabee.loading.checks

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import com.intellij.psi.impl.compiled.ClsFileImpl
import org.jetbrains.uast.UCallExpression

class LoadingBackHandlerDetector : Detector(), SourceCodeScanner {

    companion object {
        val issue: Issue = Issue.create(
            id = "ByPassLoadingHandler",
            briefDescription = "Use `LoadingBackHandler` instead of `BackHandler`",
            explanation = "Use `LoadingBackHandler` to avoid global loading bypass when adding a `BackHandler` after the `LoadingView`",
            category = Category.USABILITY,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                LoadingBackHandlerDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )
    }

    override fun getApplicableMethodNames(): List<String> = listOf("BackHandler")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if ((method.containingFile as? ClsFileImpl)?.packageName == "androidx.activity.compose") {
            val quickfixData = LintFix
                .create()
                .replace()
                .name("Replace by `LoadingBackHandler`")
                .sharedName("Replace all by `LoadingBackHandler`")
                .text("BackHandler")
                .with("com.lunabee.lbloading.LoadingBackHandler")
                .shortenNames()
                .reformat(true)
                .build()

            context.report(
                issue = issue,
                scope = node,
                location = context.getLocation(node),
                message = "`LoadingBackHandler` should be use to avoid global loading bypass",
                quickfixData = quickfixData,
            )
        }
    }
}
