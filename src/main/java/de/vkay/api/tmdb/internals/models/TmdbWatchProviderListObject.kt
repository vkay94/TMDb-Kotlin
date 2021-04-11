package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbWatchProviderList

@JsonClass(generateAdapter = true)
internal data class TmdbWatchProviderListObject(
    val results: Map<String, TmdbWatchProviderList>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbWatchProviderListObject): Map<String, TmdbWatchProviderList> {
                return obj.results
            }

            @ToJson
            fun to(@ListMap map: Map<String, TmdbWatchProviderList>): TmdbWatchProviderListObject {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}