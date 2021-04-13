package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbCollection internal constructor(
    val id: Int,
    val name: String,
    val overview: String,
    @Json(name = "parts")
    val movies: List<TmdbMovie.Slim>,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "backdrop_path")
    internal val _backgroundPath: String?
) {
    val movieCount: Int
        get() = movies.size

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath, 0, 0, null)
        else null

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath, 0, 0, null)
        else null

    @JsonClass(generateAdapter = true)
    internal data class Images(
        val backdrops: List<TmdbImage>,
        val posters: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class BelongsTo(
        @Json(name = "id")
        val collectionId: Int,
        val name: String,
        @Json(name = "poster_path")
        internal val _posterPath: String?,
        @Json(name = "backdrop_path")
        internal val _backgroundPath: String?
    ) {
        val poster: TmdbImage?
            get() = if (!_posterPath.isNullOrBlank())
                TmdbImage(_posterPath, 0, 0, null)
            else null

        val background: TmdbImage?
            get() = if (!_backgroundPath.isNullOrBlank())
                TmdbImage(_backgroundPath, 0, 0, null)
            else null
    }
}