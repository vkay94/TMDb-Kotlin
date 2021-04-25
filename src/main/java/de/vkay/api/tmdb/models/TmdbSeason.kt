package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbSeason internal constructor(
    @Json(name = "_id")
    val idTag: String,
    @Json(name = "id")
    val seasonId: Int,
    @Json(name = "poster_path")
    @TMDbImage
    val poster: TmdbImage?,
    @Json(name = "air_date")
    val premierDate: TmdbDate?,
    val episodes: List<TmdbEpisode.Slim>,
    @Json(name = "name")
    val title: String,
    val overview: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,

    // Append
    @Json(name = "images")
    internal val _images: Images?,
    @Json(name = "videos")
    @ResultsList
    internal val _videos: List<TmdbVideo>?,
    @Json(name = "credits")
    internal val _credits: Credits?

) : MediaTypeItem(MediaType.SEASON) {

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val posters: List<TmdbImage> = _images?.posters ?: emptyList()

    val cast: List<TmdbPerson.Cast> = _credits?.cast ?: emptyList()
    val crew: List<TmdbPerson.Crew> = _credits?.crew ?: emptyList()

    @JsonClass(generateAdapter = true)
    internal data class Images(
        val posters: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    internal data class Credits(
        val cast: List<TmdbPerson.Cast>,
        val crew: List<TmdbPerson.Crew>
    )

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
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
        @TMDbImage
        val poster: TmdbImage?,
        @Json(name = "season_number")
        val seasonNumber: Int
    ) : MediaTypeItem(MediaType.SEASON)
}