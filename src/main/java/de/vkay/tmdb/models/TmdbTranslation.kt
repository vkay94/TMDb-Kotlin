package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbTranslation internal constructor(
    @Json(name = "iso_3166_1")
    val countryCode: String,
    @Json(name = "iso_639_1")
    val languageCode: String,
    val name: String,
    @Json(name = "english_name")
    val englishName: String,
    val data: TmdbTranslationData
)