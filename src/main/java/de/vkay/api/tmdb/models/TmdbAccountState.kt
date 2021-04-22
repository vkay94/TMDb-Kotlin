package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.internals.annotations.Rated

class TmdbAccountState {

    @JsonClass(generateAdapter = true)
    data class Movie internal constructor(
        val id: Int,
        val favorite: Boolean,
        val watchlist: Boolean,
        @Rated
        @Json(name = "rated")
        val rating: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Show internal constructor(
        val id: Int,
        val favorite: Boolean,
        val watchlist: Boolean,
        @Rated
        @Json(name = "rated")
        val rating: Int?
    )

    @JsonClass(generateAdapter = true)
    data class EpisodeItem internal constructor(
        @Json(name = "id")
        val episodeId: Int,
        @Json(name = "episode_number")
        val episode: Int,
        @Rated
        @Json(name = "rated")
        val rating: Int?
    )

    @JsonClass(generateAdapter = true)
    data class Episode internal constructor(
        @Rated
        @Json(name = "rated")
        val rating: Int?
    )
}