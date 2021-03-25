package de.vkay.tmdb.models

sealed class TmdbTranslationData {

    data class Overview(
        val title: String,
        val overview: String
    ) : TmdbTranslationData()

    data class Biography(
        val biography: String,
    ) : TmdbTranslationData()
}
