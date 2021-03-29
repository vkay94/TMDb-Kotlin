package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbGenre

@JsonClass(generateAdapter = true)
internal data class TmdbGenres(
    val genres: List<TmdbGenre>
)