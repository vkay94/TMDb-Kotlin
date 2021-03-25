package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbWatchProviderList

@JsonClass(generateAdapter = true)
internal data class TmdbWatchProviderListObject(
    val results: Map<String, TmdbWatchProviderList>
)