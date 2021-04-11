package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.ListSortBy

@JsonClass(generateAdapter = true)
data class TmdbList internal constructor(
    @Json(name = "average_rating")
    val averageRating: Double,
    @Json(name = "backdrop_path")
    internal val _backgroundPath: String?,
    val description: String,
    val id: Int,
    @Json(name = "iso_3166_1")
    val countryCode: String,
    @Json(name = "iso_639_1")
    val languageCode: String,
    val name: String,
    val page: Int,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    val public: Boolean,
    val results: List<MediaTypeItem>,
    val revenue: Int,
    val runtime: Int,
    @Json(name = "sort_by")
    val sortBy: ListSortBy,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
) {
    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null

    val poster: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null
}