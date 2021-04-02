package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbNetwork

internal class TmdbNetworkImagesListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(images: TmdbNetwork.Images?): List<TmdbImage> {
        return images?.logos ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbImage>): TmdbNetwork.Images {
        return TmdbNetwork.Images(list)
    }
}