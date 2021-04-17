package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbList internal constructor(
    @Json(name = "average_rating")
    val averageRating: Double,
    @Json(name = "backdrop_path")
    internal val _backdropPath: String?,
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
    val backdrop: TmdbImage?
        get() = if (!_backdropPath.isNullOrBlank())
            TmdbImage(_backdropPath)
        else null

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val description: String,
        @Json(name = "favorite_count")
        val favoriteCount: Int,
        @Json(name = "item_count")
        val totalItems: Int,
        val id: Int,
        @Json(name = "iso_639_1")
        val languageCode: String,
        val name: String,
        @Json(name = "poster_path")
        internal val _posterPath: String?,
        @Json(name = "list_type")
        val listType: MediaType,
    ) {
        val poster: TmdbImage?
            get() = if (!_posterPath.isNullOrBlank())
                TmdbImage(_posterPath)
            else null
    }
}