package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.enumerations.MediaType

@Suppress("PrivatePropertyName", "UNUSED_PARAMETER", "unused")
class TmdbBody {

    //region Account

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

    //endregion

    //region Lists

    @JsonClass(generateAdapter = true)
    data class CreateList(
        val name: String,
        @Json(name = "iso_639_1")
        val languageCode: String,
        val description: String? = null,
        @Json(name = "iso_3166_1")
        val countryCode: String? = null,
        val public: Boolean = true
    )

    @JsonClass(generateAdapter = true)
    data class UpdateList(
        val name: String?,
        val description: String?,
        val public: Boolean?,
        @Json(name = "sort_by")
        val sortBy: ListSortBy?
    )

    @JsonClass(generateAdapter = true)
    data class MediaItem internal constructor(
        @Json(name = "media_type")
        val mediaType: MediaType,
        @Json(name = "media_id")
        val mediaId: Int,
        val success: Boolean?
    ) {
        constructor(mediaType: MediaType, mediaId: Int) : this(mediaType, mediaId, null)

        @JsonClass(generateAdapter = true)
        data class Builder internal constructor(
            val items: MutableList<MediaItem> = mutableListOf()
        ){
            constructor() : this(mutableListOf())

            fun addItem(mediaType: MediaType, mediaId: Int) = apply {
                if (!items.contains(MediaItem(mediaType, mediaId)))
                    items.add(MediaItem(mediaType, mediaId))
            }

            fun removeItem(mediaType: MediaType, mediaId: Int) = apply {
                items.remove(MediaItem(mediaType, mediaId))
            }

            fun clear() = apply {
                items.clear()
            }
        }
    }

    //endregion
}