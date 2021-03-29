package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbWatchProviderListObject
import de.vkay.api.tmdb.models.TmdbWatchProviderList

internal class TmdbWatchProviderMapListJsonAdapter {

    @ListMapParser
    @FromJson
    fun objToMap(watchProviderList: TmdbWatchProviderListObject?): Map<String, TmdbWatchProviderList> {
        return watchProviderList!!.results
    }

    @ToJson
    fun mapToObj(@ListMapParser list: Map<String, TmdbWatchProviderList>?): TmdbWatchProviderListObject? {
        throw Exception("Not implemented")
    }
}