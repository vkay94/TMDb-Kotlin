package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbErrorResponse internal constructor(
    @Json(name = "status_code")
    val code: Int,
    @Json(name = "status_message")
    val message: String
)