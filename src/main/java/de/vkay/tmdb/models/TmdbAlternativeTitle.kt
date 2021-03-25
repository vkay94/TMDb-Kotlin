package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbAlternativeTitle(
    @Json(name = "iso_3166_1")
    val countryCode: String,
    val title: String
)