package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.internals.models.TmdbContentRatings
import de.vkay.tmdb.models.TmdbContentRating

internal class TmdbContentRatingsListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(ratings: TmdbContentRatings?): List<TmdbContentRating> {
        return ratings?.results ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbContentRating>): TmdbContentRatings {
        return TmdbContentRatings(list)
    }
}