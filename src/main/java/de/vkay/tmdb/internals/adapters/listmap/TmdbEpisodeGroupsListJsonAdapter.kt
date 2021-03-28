package de.vkay.tmdb.internals.adapters.listmap

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.internals.models.TmdbEpisodeGroups
import de.vkay.tmdb.models.TmdbEpisodeGroupListObject

internal class TmdbEpisodeGroupsListJsonAdapter {

    @ListMapParser
    @FromJson
    fun listFromResults(list: TmdbEpisodeGroups?): List<TmdbEpisodeGroupListObject> {
        return list?.results ?: emptyList()
    }

    @ToJson
    fun listToResult(@ListMapParser list: List<TmdbEpisodeGroupListObject>): TmdbEpisodeGroups {
        return TmdbEpisodeGroups(list)
    }
}