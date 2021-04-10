package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class TmdbCertificationList(
    @Json(name = "certifications")
    internal val _certifications: Map<String, List<TmdbCertification>>
) {

    fun search(locale: Locale): List<TmdbCertification> = search(locale.country)
    fun search(countryCode: String): List<TmdbCertification> = _certifications[countryCode] ?: emptyList()
}