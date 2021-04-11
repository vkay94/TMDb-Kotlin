package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbMessage internal constructor(
    @Json(name = "status_code")
    val code: Int,
    @Json(name = "status_message")
    val message: String,
    val success: Boolean
) {
    @JsonClass(generateAdapter = true)
    data class RequestToken internal constructor(
        @Json(name = "request_token")
        val requestToken: String,
        @Json(name = "status_code")
        val code: Int,
        @Json(name = "status_message")
        val message: String,
        val success: Boolean
    )

    @JsonClass(generateAdapter = true)
    data class CreateList internal constructor(
        @Json(name = "id")
        val listId: Int,
        @Json(name = "status_code")
        val code: Int,
        @Json(name = "status_message")
        val message: String,
        val success: Boolean
    )
}