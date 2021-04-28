package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.Rated
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage
import de.vkay.api.tmdb.internals.models.TmdbCredits

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
    @TMDbImage
    val still: TmdbImage?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,

    // Append
    @Json(name = "images")
    @ResultsList("stills")
    internal val _stills: List<TmdbImage>?,
    @Json(name = "credits")
    internal val _credits: TmdbCredits?,
    @Json(name = "videos")
    @ResultsList
    internal val _videos: List<TmdbVideo>?

) : MediaTypeItem(MediaType.EPISODE) {

    val stills: List<TmdbImage> = _stills ?: emptyList()
    val videos: List<TmdbVideo> = _videos ?: emptyList()

    val crew: List<TmdbPerson.Crew> = _credits?.crew ?: emptyList()
    val cast: List<TmdbPerson.Cast> = _credits?.cast ?: emptyList()
    val guestStars: List<TmdbPerson.Guest> = _credits?.guest_stars ?: emptyList()

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        @Json(name = "air_date")
        val releaseDate: TmdbDate?,
        @Json(name = "episode_number")
        val episodeNumber: Int,
        val id: Int,
        @Json(name = "name")
        val title: String,
        val overview: String,
        @Json(name = "still_path")
        @TMDbImage
        val still: TmdbImage?,
        @Json(name = "season_number")
        val seasonNumber: Int,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int,

        // Optional when getting rated episodes (AccountService)
        @Rated
        val rating: Int?
    ) : MediaTypeItem(MediaType.EPISODE)
}