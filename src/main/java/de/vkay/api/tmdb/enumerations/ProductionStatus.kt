package de.vkay.api.tmdb.enumerations

import com.squareup.moshi.Json

enum class ProductionStatus {

    @Json(name = "Returning Series")
    RETURNING,

    @Json(name = "Ended")
    ENDED,

    @Json(name = "Canceled")
    CANCELED,

    @Json(name = "In Production")
    IN_PRODUCTION,

    @Json(name = "Pilot")
    PILOT,

    @Json(name = "Planned")
    PLANNED,

    @Json(name = "Released")
    RELEASED,

    UNKNOWN
}