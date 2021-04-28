package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage
import de.vkay.api.tmdb.internals.models.TmdbCredits

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
    @ResultsList("posters")
    internal val _posters: List<TmdbImage>?,
    @Json(name = "videos")
    @ResultsList
    internal val _videos: List<TmdbVideo>?,
    @Json(name = "credits")
    internal val _credits: TmdbCredits?

) : MediaTypeItem(MediaType.SEASON) {

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val posters: List<TmdbImage> = _posters ?: emptyList()

    val cast: List<TmdbPerson.Cast> = _credits?.cast ?: emptyList()
    val crew: List<TmdbPerson.Crew> = _credits?.crew ?: emptyList()

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