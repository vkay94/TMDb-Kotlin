package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbCompany internal constructor(
    val id: Int,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String,
    @Json(name = "logo_path")
    internal val _logoPath: String?,

    // Additional fields in details
    val headquarters: String?,
    val homepage: String?,
    val description: String?
) {
    val logo: TmdbImage?
        get() = if (!_logoPath.isNullOrBlank())
            TmdbImage(_logoPath, 0, 0, null)
        else null
}