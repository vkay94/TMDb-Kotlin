package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
open class MediaTypeItem(
    @Json(name = "media_type")
    val mediaType: MediaType
)