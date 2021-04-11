package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbImage

@JsonClass(generateAdapter = true)
internal data class TmdbNetworkImages(
    val logos: List<TmdbImage>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbNetworkImages?): List<TmdbImage> {
                return obj?.logos ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbImage>): TmdbNetworkImages {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}