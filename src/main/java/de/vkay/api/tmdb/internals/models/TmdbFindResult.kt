package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.*

@JsonClass(generateAdapter = true)
internal data class TmdbFindResult internal constructor(
    @Json(name = "movie_results")
    val movies: List<TmdbMovieListObject>,

    @Json(name = "tv_results")
    val shows: List<TmdbShowListObject>,

    @Json(name = "person_results")
    val persons: List<TmdbPersonListObject>,

    @Json(name = "tv_season_results")
    val seasons: List<TmdbSeasonListObject>,

    @Json(name = "tv_episode_results")
    val episodes: List<TmdbEpisodeListObject>
)