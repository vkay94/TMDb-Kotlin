package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbExternalIds internal constructor(
    @Json(name = "imdb_id")
    val imdb: String?,
    @Json(name = "tvdb_id")
    val tvdb: Int?,
    @Json(name = "facebook_id")
    val facebook: String?,
    @Json(name = "instagram_id")
    val instagram: String?,
    @Json(name = "twitter_id")
    val twitter: String?
)