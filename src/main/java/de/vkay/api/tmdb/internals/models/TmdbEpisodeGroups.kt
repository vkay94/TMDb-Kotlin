package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbEpisodeGroupListObject

@JsonClass(generateAdapter = true)
internal data class TmdbEpisodeGroups(
    val results: List<TmdbEpisodeGroupListObject>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbEpisodeGroups?): List<TmdbEpisodeGroupListObject> {
                return obj?.results ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbEpisodeGroupListObject>): TmdbEpisodeGroups {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}