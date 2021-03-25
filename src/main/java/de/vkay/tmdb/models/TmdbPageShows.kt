package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType

@JsonClass(generateAdapter = true)
data class TmdbPageShows(
    @Json(name = "total_results")
    val totalResults: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    val results: List<TmdbShowListObject>
) : MediaItem {
    override val mediaType: MediaType = MediaType.TV
}