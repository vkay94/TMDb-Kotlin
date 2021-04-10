package de.vkay.api.tmdb.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbCertification internal constructor(
    val certification: String,
    val meaning: String,
    val order: Int // TODO: Priority?
)