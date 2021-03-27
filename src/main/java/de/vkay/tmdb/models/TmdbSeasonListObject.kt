package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbSeasonListObject(
    @Json(name = "air_date")
    val premierDate: TmdbDate?,
    @Json(name = "episode_count")
    val episodeCount: Int,
    @Json(name = "id")
    val seasonId: Int,
    @Json(name = "name")
    val title: String,
    val overview: String?,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int
) : MediaTypeItem(MediaType.SEASON) {

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null
}