package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbMovieListObject internal constructor(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "backdrop_path")
    internal val _backgroundPath: String?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: TmdbDate?,
    val adult: Boolean,
    val popularity: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    val video: Boolean
) : MediaTypeItem(MediaType.MOVIE) {

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null
}