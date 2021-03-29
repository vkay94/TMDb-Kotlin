package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType
import de.vkay.tmdb.internals.ListMapParser

@JsonClass(generateAdapter = true)
data class TmdbSeason internal constructor(
    @Json(name = "_id")
    val idTag: String,
    @Json(name = "id")
    val seasonId: Int,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "air_date")
    val premierDate: TmdbDate?,
    val episodes: List<TmdbEpisodeListObject>,
    @Json(name = "name")
    val title: String,
    val overview: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,

    // Append
    @Json(name = "images")
    internal val _images: Images?,
    @Json(name = "videos")
    @ListMapParser
    internal val _videos: List<TmdbVideo>?,
    @Json(name = "credits")
    internal val _credits: Credits?

) : MediaTypeItem(MediaType.SEASON) {

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val posters: List<TmdbImage> = _images?.posters ?: emptyList()

    val cast: List<TmdbCredit.Cast> = _credits?.cast ?: emptyList()
    val crew: List<TmdbCredit.Crew> = _credits?.crew ?: emptyList()

    @JsonClass(generateAdapter = true)
    data class Images(
        val posters: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class Credits(
        val cast: List<TmdbCredit.Cast>,
        val crew: List<TmdbCredit.Crew>
    )
}