package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.JsonQualifier
import de.vkay.api.tmdb.enumerations.MediaType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@JsonQualifier
internal annotation class CharJob(
    val fieldName: String = "results",
    val mediaType: MediaType = MediaType.UNKNOWN
)