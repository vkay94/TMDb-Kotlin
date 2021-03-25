package de.vkay.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbKeyword(
    val id: Int,
    val name: String
)