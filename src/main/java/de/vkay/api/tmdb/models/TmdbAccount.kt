package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbAccount internal constructor(
    @Json(name = "id")
    val accountId: Int,
    val name: String,
    @Json(name = "iso_639_1")
    val languageCode: String,
    @Json(name = "iso_3166_1")
    val countryCode: String,
    val username: String,
    @Json(name = "include_adult")
    val includeAdult: Boolean

    // TODO: Add avatar (gravatar)
)