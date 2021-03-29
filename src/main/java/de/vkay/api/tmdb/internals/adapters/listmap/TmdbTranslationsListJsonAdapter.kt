package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbTranslations
import de.vkay.api.tmdb.models.TmdbTranslation

internal class TmdbTranslationsListJsonAdapter {
    @ListMapParser
    @FromJson
    fun listFromResults(list: TmdbTranslations?): List<TmdbTranslation> {
        return list?.translations ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbTranslation>): TmdbTranslations {
        return TmdbTranslations(list)
    }
}