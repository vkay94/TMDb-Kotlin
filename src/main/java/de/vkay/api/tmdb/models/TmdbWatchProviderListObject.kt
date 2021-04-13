package de.vkay.api.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbWatchProviderListObject internal constructor(
    val link: String,
    val flatrate: List<TmdbWatchProvider>?,
    val buy: List<TmdbWatchProvider>?,
    val rent: List<TmdbWatchProvider>?
)