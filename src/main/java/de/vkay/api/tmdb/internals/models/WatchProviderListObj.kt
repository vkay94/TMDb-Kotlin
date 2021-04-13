package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.models.TmdbWatchProviderListObject

@JsonClass(generateAdapter = true)
internal data class WatchProviderListObj(
    val results: Map<String, TmdbWatchProviderListObject>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @OtherCases
            @FromJson
            fun from(obj: WatchProviderListObj): Map<String, TmdbWatchProviderListObject> {
                return obj.results
            }

            @ToJson
            fun to(@OtherCases map: Map<String, TmdbWatchProviderListObject>): WatchProviderListObj {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}