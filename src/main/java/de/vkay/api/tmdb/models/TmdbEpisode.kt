package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.ListMap

@JsonClass(generateAdapter = true)
data class TmdbEpisode internal constructor(
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
    val seasonNumber: Int,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,

    @Json(name = "images")
    internal val _images: Images?,
    @Json(name = "credits")
    internal val _credits: Credits?,
    @Json(name = "videos")
    @ListMap
    internal val _videos: List<TmdbVideo>?

) : MediaTypeItem(MediaType.EPISODE) {

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null

    val backgrounds: List<TmdbImage> = _images?.stills ?: emptyList()
    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val crew: List<TmdbCredit.Crew> = _credits?.crew ?: emptyList()
    val cast: List<TmdbCredit.Cast> = _credits?.cast ?: emptyList()

    @JsonClass(generateAdapter = true)
    data class Images(
        val stills: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class Credits(
        val cast: List<TmdbCredit.Cast>,
        val crew: List<TmdbCredit.Crew>
//        @Json(name = "guest_stars")
//        val guestStars: List<TmdbCredit.Guest>?
    )
}