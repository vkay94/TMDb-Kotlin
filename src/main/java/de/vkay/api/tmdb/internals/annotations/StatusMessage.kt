package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.JsonQualifier

/**
 * Used for status message transformations as such with success field
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
internal annotation class StatusMessage