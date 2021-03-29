package de.vkay.api.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbGenre internal constructor(
    val id: Int,
    val name: String
)