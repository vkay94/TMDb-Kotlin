package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbAlternativeTitle

@JsonClass(generateAdapter = true)
internal data class TmdbAlternativeTitles(
    val results: List<TmdbAlternativeTitle>
)