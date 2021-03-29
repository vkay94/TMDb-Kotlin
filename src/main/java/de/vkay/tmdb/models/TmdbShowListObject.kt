package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbShowListObject internal constructor(
    val id: Int,
    @Json(name = "name")
    val title: String,
    @Json(name = "backdrop_path")
    internal val _backgroundPath: String?,
    @Json(name = "first_air_date")
    val releaseDate: TmdbDate?,

    @Json(name = "original_name")
    val originalTitle: String,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "vote_average")
    val voteAverage: Double

) : MediaTypeItem(MediaType.TV) {

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null
}