package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbNetwork internal constructor(
    val id: Int,
    val name: String,
    val headquarters: String,
    @Json(name = "origin_country")
    val originCountry: String,
    val homepage: String,
    @Json(name = "logo_path")
    internal val _logoPath: String?
) {
    val logo: TmdbImage?
        get() = if (!_logoPath.isNullOrBlank())
            TmdbImage(_logoPath, 0, 0, null)
        else null

    @JsonClass(generateAdapter = true)
    internal data class Images(
        val logos: List<TmdbImage>
    )
}