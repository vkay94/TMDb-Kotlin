package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.models.TmdbError

/**
 * Required because of NetworkResponse's fallback
 */
internal class ErrorAnnotationAdapter {

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

    //region ResultsList

    @ResultsList
    @FromJson
    fun normalR(input: String?): TmdbError? {
        return null
    }

    @ToJson
    fun normalR(@ResultsList input: TmdbError): String {
        throw UnsupportedOperationException()
    }

    @ResultsList
    @FromJson
    fun defaultR(input: String?): TmdbError.DefaultError? {
        return null
    }

    @ToJson
    fun defaultR(@ResultsList input: TmdbError.DefaultError): String {
        throw UnsupportedOperationException()
    }

    @ResultsList
    @FromJson
    fun postR(input: String?): TmdbError.PostError? {
        return null
    }

    @ToJson
    fun postR(@ResultsList input: TmdbError.PostError): String {
        throw UnsupportedOperationException()
    }

    //endregion
}