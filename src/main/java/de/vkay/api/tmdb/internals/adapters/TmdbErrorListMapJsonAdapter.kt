package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbError

internal class TmdbErrorListMapJsonAdapter {

    @ListMapParser
    @FromJson
    fun normal(input: String?): TmdbError? {
        return null
    }

    @ToJson
    fun normal(@ListMapParser input: TmdbError): String {
        throw UnsupportedOperationException()
    }

    @ListMapParser
    @FromJson
    fun default(input: String?): TmdbError.DefaultError? {
        return null
    }

    @ToJson
    fun default(@ListMapParser input: TmdbError.DefaultError): String {
        throw UnsupportedOperationException()
    }

    @ListMapParser
    @FromJson
    fun post(input: String?): TmdbError.PostError? {
        return null
    }

    @ToJson
    fun post(@ListMapParser input: TmdbError.PostError): String {
        throw UnsupportedOperationException()
    }
}