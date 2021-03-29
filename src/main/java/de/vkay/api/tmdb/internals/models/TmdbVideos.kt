package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbVideo

@JsonClass(generateAdapter = true)
internal data class TmdbVideos(
    val results: List<TmdbVideo>
)