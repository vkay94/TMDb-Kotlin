package de.vkay.api.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbErrorResponse

internal class TmdbErrorJsonAdapter {

    @ListMapParser
    @FromJson
    fun fromString(str: String?): TmdbErrorResponse? {
        return null
    }

    @ToJson
    fun listToString(@ListMapParser list: TmdbErrorResponse): String {
        throw UnsupportedOperationException()
    }
}