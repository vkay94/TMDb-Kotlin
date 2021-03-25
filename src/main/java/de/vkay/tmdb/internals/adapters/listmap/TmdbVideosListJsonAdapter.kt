package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.internals.models.TmdbVideos
import de.vkay.tmdb.models.TmdbVideo

internal class TmdbVideosListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromVideos(videos: TmdbVideos?): List<TmdbVideo> {
        return videos?.results ?: emptyList()
    }

    @ToJson
    fun listToVideos(@ListMapParser list: List<TmdbVideo>?): TmdbVideos? {
        throw Exception("Not implemented")
    }
}