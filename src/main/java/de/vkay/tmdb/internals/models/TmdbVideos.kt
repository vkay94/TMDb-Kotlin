package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbVideo

@JsonClass(generateAdapter = true)
internal data class TmdbVideos(
    val results: List<TmdbVideo>
)