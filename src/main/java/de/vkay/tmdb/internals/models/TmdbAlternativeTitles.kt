package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbAlternativeTitle

@JsonClass(generateAdapter = true)
internal data class TmdbAlternativeTitles(
    val results: List<TmdbAlternativeTitle>
)