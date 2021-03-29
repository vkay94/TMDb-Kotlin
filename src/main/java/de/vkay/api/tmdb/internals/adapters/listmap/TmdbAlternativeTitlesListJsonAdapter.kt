package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbAlternativeTitles
import de.vkay.api.tmdb.models.TmdbAlternativeTitle

internal class TmdbAlternativeTitlesListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(list: TmdbAlternativeTitles?): List<TmdbAlternativeTitle> {
        return list?.results ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbAlternativeTitle>): TmdbAlternativeTitles {
        return TmdbAlternativeTitles(list)
    }
}