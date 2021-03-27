package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class TmdbPage<T> {
    @Json(name = "page")
    internal var _page: Int = -1 // by Delegates.notNull<Int>()
    @Json(name = "total_results")
    internal var _totalResults: Int = -1
    @Json(name = "total_pages")
    internal var _totalPages: Int = 0
    @Json(name = "results")
    internal var _results: List<T> = listOf()

    val page: Int get() = _page
    val totalResults: Int get() = _totalResults
    val totalPages: Int get() = _totalPages
    val results: List<T> get() = _results

    val hasNextPage: Boolean get() = _page < _totalPages
}