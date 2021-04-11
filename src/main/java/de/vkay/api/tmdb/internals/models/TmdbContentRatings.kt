package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbContentRating

@JsonClass(generateAdapter = true)
internal data class TmdbContentRatings(
    val results: List<TmdbContentRating>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbContentRatings?): List<TmdbContentRating> {
                return obj?.results ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbContentRating>): TmdbContentRatings {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}