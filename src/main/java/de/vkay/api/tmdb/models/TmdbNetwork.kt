package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbNetwork internal constructor(
    val id: Int,
    val name: String,
    val headquarters: String,
    @Json(name = "origin_country")
    val originCountry: String,
    val homepage: String,
    @Json(name = "logo_path")
    @TMDbImage
    val logo: TmdbImage?
)