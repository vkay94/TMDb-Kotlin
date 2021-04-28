package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbImage

@JsonClass(generateAdapter = true)
internal class TmdbImages internal constructor(
    val posters: List<TmdbImage>?,
    val backdrops: List<TmdbImage>?
)