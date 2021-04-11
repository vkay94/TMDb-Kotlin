package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.models.TmdbError

/**
 * Required because of NetworkResponse's fallback
 */
internal class ErrorAnnotationAdapter {

    //region ListMap

    @ListMap
    @FromJson
    fun normal(input: String?): TmdbError? {
        return null
    }

    @ToJson
    fun normal(@ListMap input: TmdbError): String {
        throw UnsupportedOperationException()
    }

    @ListMap
    @FromJson
    fun default(input: String?): TmdbError.DefaultError? {
        return null
    }

    @ToJson
    fun default(@ListMap input: TmdbError.DefaultError): String {
        throw UnsupportedOperationException()
    }

    @ListMap
    @FromJson
    fun post(input: String?): TmdbError.PostError? {
        return null
    }

    @ToJson
    fun post(@ListMap input: TmdbError.PostError): String {
        throw UnsupportedOperationException()
    }

    //endregion

    //region StatusMessage

    @StatusMessage
    @FromJson
    fun normalStatus(input: String?): TmdbError? {
        return null
    }

    @ToJson
    fun normalStatus(@StatusMessage input: TmdbError): String {
        throw UnsupportedOperationException()
    }

    @StatusMessage
    @FromJson
    fun defaultNormal(input: String?): TmdbError.DefaultError? {
        return null
    }

    @ToJson
    fun defaultStatus(@StatusMessage input: TmdbError.DefaultError): String {
        throw UnsupportedOperationException()
    }

    @StatusMessage
    @FromJson
    fun postStatus(input: String?): TmdbError.PostError? {
        return null
    }

    @ToJson
    fun postStatus(@StatusMessage input: TmdbError.PostError): String {
        throw UnsupportedOperationException()
    }

    //endregion

    //region OtherCases

    @OtherCases
    @FromJson
    fun normalOther(input: String?): TmdbError? {
        return null
    }

    @ToJson
    fun normalOther(@OtherCases input: TmdbError): String {
        throw UnsupportedOperationException()
    }

    @OtherCases
    @FromJson
    fun defaultOther(input: String?): TmdbError.DefaultError? {
        return null
    }

    @ToJson
    fun defaultOther(@OtherCases input: TmdbError.DefaultError): String {
        throw UnsupportedOperationException()
    }

    @OtherCases
    @FromJson
    fun postOther(input: String?): TmdbError.PostError? {
        return null
    }

    @ToJson
    fun postOther(@OtherCases input: TmdbError.PostError): String {
        throw UnsupportedOperationException()
    }

    //endregion
}