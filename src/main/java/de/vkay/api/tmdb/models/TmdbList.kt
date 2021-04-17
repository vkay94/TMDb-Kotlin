package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbList internal constructor(
    @Json(name = "average_rating")
    val averageRating: Double,
    @Json(name = "backdrop_path")
    @TMDbImage
    val backdrop: TmdbImage?,
    val description: String,
    val id: Int,
    @Json(name = "iso_3166_1")
    val countryCode: String,
    @Json(name = "iso_639_1")
    val languageCode: String,
    val name: String,
    val page: Int,
    @Json(name = "poster_path")
    @TMDbImage
    val poster: TmdbImage?,
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
        @TMDbImage
        val poster: TmdbImage?,
        @Json(name = "list_type")
        val listType: MediaType,
    )
}