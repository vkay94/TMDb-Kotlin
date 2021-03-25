package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbContentRating(
    @Json(name = "iso_3166_1")
    val countryCode: String,
    val rating: String
)