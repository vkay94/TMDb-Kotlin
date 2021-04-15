package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class TmdbError {

    companion object {
        fun isAnyError(clazz: Class<*>): Boolean {
            return clazz == TmdbError::class.java
                    || clazz == DefaultError::class.java
                    || clazz == PostError::class.java
        }
    }

    @JsonClass(generateAdapter = true)
    data class PostError internal constructor(
        val errors: List<String>
    ) : TmdbError()

    @JsonClass(generateAdapter = true)
    data class DefaultError internal constructor(
        @Json(name = "status_code")
        val code: Int,
        @Json(name = "status_message")
        val message: String
    ) : TmdbError()
}