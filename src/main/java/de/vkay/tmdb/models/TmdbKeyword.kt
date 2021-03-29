package de.vkay.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbKeyword internal constructor(
    val id: Int,
    val name: String
)