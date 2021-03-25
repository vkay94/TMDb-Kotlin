package de.vkay.tmdb.enumerations

import com.squareup.moshi.Json

enum class ShowType {

    @Json(name = "Scripted")
    SCRIPTED,

    UNKNOWN
}