package com.lunabee.loading.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class LoadingIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        LoadingBackHandlerDetector.issue,
    )

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        vendorName = "Lunabee Studio",
        feedbackUrl = "https://lunabee-studio.slack.com/archives/C01SV7JN7V3",
        contact = "https://lunabee-studio.slack.com/archives/C01SV7JN7V3",
    )
}
