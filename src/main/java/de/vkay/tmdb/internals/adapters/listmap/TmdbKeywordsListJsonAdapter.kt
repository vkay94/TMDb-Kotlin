package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.internals.models.TmdbKeywords
import de.vkay.tmdb.models.TmdbKeyword

internal class TmdbKeywordsListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromKeywords(keywords: TmdbKeywords?): List<TmdbKeyword> {
        return keywords?.results ?: emptyList()
    }

    @ToJson
    fun listToKeywords(@ListMapParser list: List<TmdbKeyword>): TmdbKeywords {
        return TmdbKeywords(list)
    }
}