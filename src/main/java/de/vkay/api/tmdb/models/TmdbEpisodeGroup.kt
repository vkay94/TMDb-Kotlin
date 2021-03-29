package de.vkay.api.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbEpisodeGroup internal constructor(
    val id: String,
    val name: String,
    val order: Int,
    val episodes: List<TmdbEpisodeListObject>,
    val locked: Boolean
)