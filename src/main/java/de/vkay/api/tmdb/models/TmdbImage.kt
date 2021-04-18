package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbImage internal constructor(
    @Json(name = "file_path")
    val filePath: String,
    val height: Int,
    val width: Int,
    @Json(name = "iso_639_1")
    val languageCode: String?
) {
    constructor(path: String) : this(path, 0, 0, null)

    /**
     * Returns the full image url of an [TmdbImage] with optional [Quality]
     *
     * @param quality Resolution of the image
     */
    fun get(quality: Quality = Quality.ORIGINAL): String {
        return get(filePath, quality)
    }

    enum class Quality(private val size: String) {
        BACKDROP_W_300("w300"),
        BACKDROP_W_780("w780"),
        BACKDROP_W_1280("w1280"),

        LOGO_W_45("w45"),
        LOGO_W_92("w92"),
        LOGO_W_154("w154"),
        LOGO_W_185("w185"),
        LOGO_W_300("w300"),
        LOGO_W_500("w500"),

        POSTER_W_92("w92"),
        POSTER_W_154("w154"),
        POSTER_W_185("w185"),
        POSTER_W_342("w342"),
        POSTER_W_500("w500"),
        POSTER_W_780("w780"),

        PROFILE_W_45("w45"),
        PROFILE_W_154("w154"),
        PROFILE_H_632("h632"),

        STILL_W_92("w92"),
        STILL_W_185("w185"),
        STILL_W_300("w300"),

        ORIGINAL("original");

        override fun toString(): String = size
    }

    companion object {
        fun get(path: String?, quality: Quality) = "https://image.tmdb.org/t/p/$quality$path"
    }
}