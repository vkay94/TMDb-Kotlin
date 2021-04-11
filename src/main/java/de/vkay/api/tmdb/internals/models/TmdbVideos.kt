package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbVideo

@JsonClass(generateAdapter = true)
internal data class TmdbVideos(
    val results: List<TmdbVideo>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbVideos?): List<TmdbVideo> {
                return obj?.results ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbVideo>): TmdbVideos {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}