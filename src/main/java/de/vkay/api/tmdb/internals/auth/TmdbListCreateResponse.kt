package de.vkay.api.tmdb.internals.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbListCreateResponse(
    val id: Int,
    val success: Boolean
)