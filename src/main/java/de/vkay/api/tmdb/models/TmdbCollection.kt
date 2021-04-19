package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbCollection internal constructor(
    val id: Int,
    val name: String,
    val overview: String,
    @Json(name = "parts")
    val movies: List<TmdbMovie.Slim>,
    @Json(name = "poster_path")
    @TMDbImage
    val poster: TmdbImage?,
    @Json(name = "backdrop_path")
    @TMDbImage
    val backdrop: TmdbImage?
) {
    val movieCount: Int
        get() = movies.size

    @JsonClass(generateAdapter = true)
    internal data class Images(
        val backdrops: List<TmdbImage>,
        val posters: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class Slim(
        @Json(name = "id")
        val collectionId: Int,
        val name: String,
        @Json(name = "backdrop_path")
        @TMDbImage
        val backdrop: TmdbImage?,
        @Json(name = "poster_path")
        @TMDbImage
        val poster: TmdbImage?,
    )
}