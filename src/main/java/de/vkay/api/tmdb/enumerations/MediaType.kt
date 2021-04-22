package de.vkay.api.tmdb.enumerations

import com.squareup.moshi.Json

enum class MediaType {

    @Json(name = "tv")
    TV,

    @Json(name = "movie")
    MOVIE,

    @Json(name = "season")
    SEASON,

    @Json(name = "episode")
    EPISODE,

    @Json(name = "person")
    PERSON,

    UNKNOWN
}