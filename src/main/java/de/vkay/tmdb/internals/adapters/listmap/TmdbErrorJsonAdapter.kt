package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.models.TmdbErrorResponse

internal class TmdbErrorJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromVideos(str: String?): TmdbErrorResponse? {
        return null
    }

    @ToJson
    fun listToVideos(@ListMapParser list: TmdbErrorResponse): String {
        throw UnsupportedOperationException()
    }
}