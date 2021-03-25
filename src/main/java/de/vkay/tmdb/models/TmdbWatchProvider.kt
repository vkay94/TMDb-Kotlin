package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbWatchProvider(
    @Json(name = "logo_path")
    internal val _logoPath: String?,
    @Json(name = "provider_name")
    val name: String,
    @Json(name = "provider_id")
    val id: Int
) {
    val logo: TmdbImage?
        get() = if (!_logoPath.isNullOrBlank())
            TmdbImage(_logoPath)
        else null
}