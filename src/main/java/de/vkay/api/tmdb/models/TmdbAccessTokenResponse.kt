package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbAccessTokenResponse(
    @Json(name = "access_token")
    val accessToken: String,
    val success: Boolean,
    @Json(name = "account_id")
    val accountId: String
)