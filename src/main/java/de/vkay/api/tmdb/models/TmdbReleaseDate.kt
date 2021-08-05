package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.ReleaseType

@JsonClass(generateAdapter = true)
data class TmdbReleaseDate internal constructor(
    @Json(name = "iso_639_1")
    val languageCode: String?,
    val note: String?,
    @Json(name = "release_date")
    val releaseDate: TmdbDate,
    val certification: String,
    @Json(name = "type")
    val releaseType: ReleaseType
)