package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.models.*

@JsonClass(generateAdapter = true)
internal data class TmdbFindResult internal constructor(
    @Json(name = "movie_results")
    val movies: List<TmdbMovie.Slim>,

    @Json(name = "tv_results")
    val shows: List<TmdbShow.Slim>,

    @Json(name = "person_results")
    val persons: List<TmdbPerson.Slim>,

    @Json(name = "tv_season_results")
    val seasons: List<TmdbSeason.Slim>,

    @Json(name = "tv_episode_results")
    val episodes: List<TmdbEpisode.Slim>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @OtherCases
            @FromJson
            fun from(findResult: TmdbFindResult?): MediaTypeItem? {
                if (findResult == null) return null
                return when {
                    findResult.movies.isNotEmpty() -> {
                        findResult.movies.first()
                    }
                    findResult.shows.isNotEmpty() -> {
                        findResult.shows.first()
                    }
                    findResult.persons.isNotEmpty() -> {
                        findResult.persons.first()
                    }
                    findResult.seasons.isNotEmpty() -> {
                        findResult.seasons.first()
                    }
                    findResult.episodes.isNotEmpty() -> {
                        findResult.episodes.first()
                    }
                    else -> null
                }
            }

            @ToJson
            fun to(@OtherCases mediaTypeItem: MediaTypeItem): TmdbFindResult {
                throw UnsupportedOperationException()
            }
        }
    }
}