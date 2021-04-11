package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class TmdbError {

    @JsonClass(generateAdapter = true)
    class PostError internal constructor(
        val errors: List<String>
    ) : TmdbError() {
        override fun toString(): String = errors.toString()
    }

    @JsonClass(generateAdapter = true)
    class DefaultError internal constructor(
        @Json(name = "status_code")
        val code: Int,
        @Json(name = "status_message")
        val message: String
    ) : TmdbError()
}