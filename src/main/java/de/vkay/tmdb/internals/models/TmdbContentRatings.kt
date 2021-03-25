package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbContentRating

@JsonClass(generateAdapter = true)
internal data class TmdbContentRatings(
    val results: List<TmdbContentRating>
)