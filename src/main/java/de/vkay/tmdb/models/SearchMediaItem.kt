package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.SearchMedia

@JsonClass(generateAdapter = true)
open class SearchMediaItem(
    @Json(name = "media_type")
    val searchType: SearchMedia
)