package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbGenre

@JsonClass(generateAdapter = true)
internal data class TmdbGenres(
    val genres: List<TmdbGenre>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbGenres?): List<TmdbGenre> {
                return obj?.genres ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbGenre>): TmdbGenres {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}