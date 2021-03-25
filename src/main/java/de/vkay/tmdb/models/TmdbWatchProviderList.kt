package de.vkay.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbWatchProviderList(
    val link: String,
    val flatrate: List<TmdbWatchProvider>?,
    val buy: List<TmdbWatchProvider>?,
    val rent: List<TmdbWatchProvider>?
)