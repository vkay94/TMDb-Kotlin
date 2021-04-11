package de.vkay.api.tmdb.internals.auth

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
internal class TmdbMessage internal constructor(
    @Json(name = "status_code")
    val code: Int,
    @Json(name = "status_message")
    val message: String,
    val success: Boolean
)

internal class TmdbStatusSuccessJsonAdapter {

    @FromJson
    fun fromJson(response: TmdbMessage?): Boolean? {
        return response?.success
    }

    @ToJson
    fun toJson(success: Boolean): TmdbMessage {
        throw UnsupportedOperationException()
    }
}