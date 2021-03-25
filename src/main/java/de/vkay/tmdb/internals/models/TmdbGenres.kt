package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbGenre

@JsonClass(generateAdapter = true)
internal data class TmdbGenres(
    val genres: List<TmdbGenre>
)