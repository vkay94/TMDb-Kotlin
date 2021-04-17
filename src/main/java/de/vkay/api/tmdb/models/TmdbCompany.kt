package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbCompany internal constructor(
    val id: Int,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String,
    @Json(name = "logo_path")
    @TMDbImage
    val logo: TmdbImage?,

    // Additional fields in details
    val headquarters: String?,
    val homepage: String?,
    val description: String?
)