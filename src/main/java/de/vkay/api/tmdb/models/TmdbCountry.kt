package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbCountry internal constructor(
    @Json(name = "iso_3166_1")
    val countryCode: String,
    val name: String
)