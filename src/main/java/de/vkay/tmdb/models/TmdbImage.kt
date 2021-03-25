package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbImage(
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
    fun get(quality: Quality = Quality.W_500): String {
        return get(filePath, quality)
    }

    enum class Quality(private val size: String) {
        W_92("w92"),
        W_185("w185"),
        W_300("w300"),
        W_500("w500"),
        W_780("w780"),
        W_1280("w1280"),
        ORIGINAL("original");

        override fun toString(): String = size
    }

    companion object {
        fun get(path: String?, quality: Quality) = "https://image.tmdb.org/t/p/$quality$path"
    }
}