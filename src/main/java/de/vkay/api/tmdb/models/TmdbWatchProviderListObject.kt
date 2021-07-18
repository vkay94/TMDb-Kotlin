package de.vkay.api.tmdb.models

import com.squareup.moshi.JsonClass

@Suppress("SpellCheckingInspection")
@JsonClass(generateAdapter = true)
data class TmdbWatchProviderListObject internal constructor(
    val link: String,
    val flatrate: List<TmdbWatchProvider>?,
    val buy: List<TmdbWatchProvider>?,
    val ads: List<TmdbWatchProvider>?,
    val rent: List<TmdbWatchProvider>?
)