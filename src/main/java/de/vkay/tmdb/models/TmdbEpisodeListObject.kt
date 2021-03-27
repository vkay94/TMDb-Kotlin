package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbEpisodeListObject(
    @Json(name = "air_date")
    val releaseDate: TmdbDate?,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    val id: Int,
    @Json(name = "name")
    val title: String,
    val overview: String,
    @Json(name = "still_path")
    internal val _backgroundPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int
) : MediaTypeItem(MediaType.EPISODE) {

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null
}