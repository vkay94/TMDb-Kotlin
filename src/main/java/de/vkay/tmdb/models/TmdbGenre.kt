package de.vkay.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbGenre(
    val id: Int,
    val name: String
)