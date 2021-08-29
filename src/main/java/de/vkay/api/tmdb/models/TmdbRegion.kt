package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents a country.
 */
@JsonClass(generateAdapter = true)
data class TmdbRegion internal constructor(
    /**
     * Country code in ISO-3166-1 format.
     *
     * Example: __US__.
     */
    @Json(name = "iso_3166_1")
    val countryCode: String,
    /**
     * Name of the country in English.
     *
     * Example: __United States of America__.
     */
    @Json(name = "english_name")
    val englishName: String,
    /**
     * Name of the country in the language defined by `languageTag` parameter, otherwise English.
     *
     * Example: __Vereinigte Staaten__ for __de__.
     */
    @Json(name = "native_name")
    val nativeName: String
)