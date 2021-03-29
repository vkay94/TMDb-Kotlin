package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbKeyword

@JsonClass(generateAdapter = true)
internal data class TmdbKeywords(
    val results: List<TmdbKeyword>
)