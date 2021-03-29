package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbWatchProviderList

@JsonClass(generateAdapter = true)
internal data class TmdbWatchProviderListObject(
    val results: Map<String, TmdbWatchProviderList>
)