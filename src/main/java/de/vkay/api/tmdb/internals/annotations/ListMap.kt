package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.JsonQualifier

/**
 * Used for List and Map transformations
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
internal annotation class ListMap