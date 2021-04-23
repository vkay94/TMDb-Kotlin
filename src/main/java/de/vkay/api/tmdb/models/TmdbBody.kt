package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType

@Suppress("PrivatePropertyName", "UNUSED_PARAMETER", "unused")
class TmdbBody {

    @JsonClass(generateAdapter = true)
    class MarkFavorite(
        @Json(name = "media_type")
        val mediaType: MediaType,
        @Json(name = "media_id")
        val mediaId: Int,
        @Json(name = "favorite")
        val isFavorite: Boolean
    ) {
        init {
            if (mediaType != MediaType.TV && mediaType != MediaType.MOVIE) {
                throw Exception("MarkFavorite media type: Only TV or MOVIE allowed but it was $mediaType")
            }
        }
    }

    @JsonClass(generateAdapter = true)
    class AddToWatchlist(
        @Json(name = "media_type")
        val mediaType: MediaType,
        @Json(name = "media_id")
        val mediaId: Int,
        @Json(name = "watchlist")
        val add: Boolean
    ) {
        init {
            if (mediaType != MediaType.TV && mediaType != MediaType.MOVIE) {
                throw Exception("AddToWatchlist media type: Only TV or MOVIE allowed but it was $mediaType")
            }
        }
    }
}