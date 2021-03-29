package de.vkay.api.tmdb.models

sealed class TmdbTranslationData {

    data class Overview internal constructor(
        val title: String,
        val overview: String
    ) : TmdbTranslationData()

    data class Biography internal constructor(
        val biography: String,
    ) : TmdbTranslationData()
}
