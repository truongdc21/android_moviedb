package com.truongdc.movie_tmdb.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.truongdc.movie_tmdb.lint.designsystem.DesignSystemDetector

class MovieTMDBIssueRegister : IssueRegistry() {

    override val issues = listOf(DesignSystemDetector.ISSUE)

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Movie TMDB application",
        feedbackUrl = "https://github.com/truongdc/android_movie_tmdb/issues",
        contact = "https://github.com/truongdc/android_movie_tmdb",
    )
}
