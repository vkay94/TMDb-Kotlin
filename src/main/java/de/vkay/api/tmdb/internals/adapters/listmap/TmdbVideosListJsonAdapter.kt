package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbVideos
import de.vkay.api.tmdb.models.TmdbVideo

internal class TmdbVideosListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(videos: TmdbVideos?): List<TmdbVideo> {
        return videos?.results ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbVideo>?): TmdbVideos? {
        throw Exception("Not implemented")
    }
}