package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbKeyword

@JsonClass(generateAdapter = true)
internal data class TmdbKeywords(
    val results: List<TmdbKeyword>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbKeywords?): List<TmdbKeyword> {
                return obj?.results ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbKeyword>): TmdbKeywords {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}