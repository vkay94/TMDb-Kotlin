package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.JsonQualifier

/**
 * Used for everything else (?)
 */
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
internal annotation class OtherCases