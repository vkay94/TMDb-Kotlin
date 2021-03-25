package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbKeyword

@JsonClass(generateAdapter = true)
internal data class TmdbKeywords(
    val results: List<TmdbKeyword>
)