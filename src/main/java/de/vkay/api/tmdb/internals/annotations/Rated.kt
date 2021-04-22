package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Used for everything else (?)
 */
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
internal annotation class Rated

class RatedJsonAdapter {

    @Rated
    @FromJson
    fun fromJson(any: Any): Int {
        val json = any.toString()
        val regEx = """\{value=([\d.]*)}""".toRegex()

        regEx.find(json)?.destructured?.let {
            val (rating) = it
            return rating.toDouble().toInt()
        }
        return -1
    }

    @ToJson
    fun toJson(@Rated value: Int): Any {
        return "EMPTY"
    }
}