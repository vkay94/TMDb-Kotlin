package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.internals.models.TmdbFindResult
import de.vkay.api.tmdb.models.MediaTypeItem

/**
 * Reference for valid date string: [Baeldung](https://www.baeldung.com/java-string-valid-date)
 */
internal class TmdbFindJsonAdapter {

    @ListMapParser
    @FromJson
    fun fromJson(findResult: TmdbFindResult?): MediaTypeItem? {
        if (findResult == null) return null
        return when {
            findResult.movies.isNotEmpty() -> {
                findResult.movies.first()
            }
            findResult.shows.isNotEmpty() -> {
                findResult.shows.first()
            }
            findResult.persons.isNotEmpty() -> {
                findResult.persons.first()
            }
            findResult.seasons.isNotEmpty() -> {
                findResult.seasons.first()
            }
            findResult.episodes.isNotEmpty() -> {
                findResult.episodes.first()
            }
            else -> null
        }
    }

    @ToJson
    fun toJson(@ListMapParser mediaTypeItem: MediaTypeItem): TmdbFindResult {
        throw UnsupportedOperationException()
    }
}