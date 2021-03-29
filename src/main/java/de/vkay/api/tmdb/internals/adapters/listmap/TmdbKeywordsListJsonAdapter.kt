package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbKeywords
import de.vkay.api.tmdb.models.TmdbKeyword

internal class TmdbKeywordsListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(keywords: TmdbKeywords?): List<TmdbKeyword> {
        return keywords?.results ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbKeyword>): TmdbKeywords {
        return TmdbKeywords(list)
    }
}