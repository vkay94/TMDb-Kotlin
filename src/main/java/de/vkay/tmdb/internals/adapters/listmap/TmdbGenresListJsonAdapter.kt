package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.internals.models.TmdbGenres
import de.vkay.tmdb.models.TmdbGenre

internal class TmdbGenresListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromGenres(genres: TmdbGenres?): List<TmdbGenre> {
        return genres?.genres ?: emptyList()
    }

    @ToJson
    fun listToGenres(@ListMapParser list: List<TmdbGenre>): TmdbGenres {
        return TmdbGenres(list)
    }
}