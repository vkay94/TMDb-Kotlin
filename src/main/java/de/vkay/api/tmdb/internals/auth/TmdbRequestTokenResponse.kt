package de.vkay.api.tmdb.internals.auth

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser

@JsonClass(generateAdapter = true)
internal data class TmdbRequestTokenResponse(
    val request_token: String,
    val success: Boolean,
    val status_code: Int
)

internal class TmdbRequestTokenResponseJsonAdapterHelper {

    @ListMapParser
    @FromJson
    fun requestToString(req: TmdbRequestTokenResponse?): String? {
        return req?.request_token
    }

    @ToJson
    fun stringToToken(@ListMapParser str: String): TmdbRequestTokenResponse {
        throw UnsupportedOperationException()
    }
}