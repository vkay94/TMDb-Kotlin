package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbVideo internal constructor(
    val key: String,
    val site: Site, // YouTube
    val type: Type, // Trailer

    @Json(name = "iso_639_1")
    val languageCode: String, // de, en, fr, ...
    @Json(name = "iso_3166_1")
    val countryCode: String, // "DE, US, ...
    @Json(name = "size")
    val resolution: Int
) {

    /**
     * Returns the https link for the YouTube video.
     *
     * For example:
     * [https://www.youtube.com/watch?v=11FNDJpsmVM](https://www.youtube.com/watch?v=11FNDJpsmVM)
     */
    val youtubeTrailer: String?
        get() = if (isYouTubeTrailer && key.isNotBlank())
            "https://www.youtube.com/watch?v=$key"
        else null

    /**
     * Returns `true` if the video is a video for YouTube, `false` otherwise.
     */
    val isYouTubeTrailer: Boolean
        get() = site == Site.YOUTUBE && type == Type.TRAILER

    /**
     * Returns the YouTube thumbnail
     *
     * For Example:
     * [https://img.youtube.com/vi/11FNDJpsmVM/sddefault.jpg](https://img.youtube.com/vi/11FNDJpsmVM/sddefault.jpg)
     *
     * Reference: [Stackoverflow](https://stackoverflow.com/a/2068371/5199098)
     */
    fun youtubeThumbnail(res: YtResolution = YtResolution.STANDARD): String? =
        if (isYouTubeTrailer && key.isNotBlank())
            "https://img.youtube.com/vi/$key/$res.jpg"
        else null

    enum class Site {
        @Json(name = "YouTube")
        YOUTUBE,
        UNDEFINED
    }

    enum class Type {
        @Json(name = "Trailer")
        TRAILER,
        @Json(name = "Teaser")
        TEASER,
        UNDEFINED
    }

    enum class YtResolution(val key: String) {

        /**
         * Size: 640 x 480
         */
        STANDARD("sddefault"),

        /**
         * Size: 120 x 90
         */
        DEFAULT("default"),

        /**
         * Size: 480 x 360
         */
        HQ("hqdefault"),
        MAX("maxresdefault");

        override fun toString(): String = key
    }
}