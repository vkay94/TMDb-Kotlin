package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
open class MediaTypeItem internal constructor(
    @Json(name = "media_type")
    val mediaType: MediaType
)