package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.JsonQualifier

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@JsonQualifier
annotation class ResultsList(val fieldName: String = "results")