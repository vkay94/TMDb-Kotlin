package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbContentRating

@JsonClass(generateAdapter = true)
internal data class TmdbContentRatings(
    val results: List<TmdbContentRating>
)