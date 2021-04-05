package de.vkay.api.tmdb.internals.auth

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser

@JsonClass(generateAdapter = true)
data class TmdbListCreateResponse(
    val id: Int,
    val success: Boolean
    )

internal class TmdbListCreateResponseJsonAdapterHelper {

    @ListMapParser
    @FromJson
    fun requestToString(req: TmdbListCreateResponse?): Int? {
        return req?.id
    }

    @ToJson
    fun stringToToken(@ListMapParser id: Int): TmdbListCreateResponse {
        throw UnsupportedOperationException()
    }
}