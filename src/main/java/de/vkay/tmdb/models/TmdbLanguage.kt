package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbLanguage internal constructor(
    @Json(name = "iso_639_1")
    val languageCode: String,
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "name")
    val nativeName: String
)