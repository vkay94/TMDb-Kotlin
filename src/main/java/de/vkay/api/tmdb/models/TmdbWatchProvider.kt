package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbWatchProvider internal constructor(
    @Json(name = "logo_path")
    @TMDbImage
    val logo: TmdbImage?,
    @Json(name = "provider_name")
    val name: String,
    @Json(name = "provider_id")
    val id: Int
)