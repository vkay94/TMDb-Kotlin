package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbAlternativeTitle

@JsonClass(generateAdapter = true)
internal data class TmdbAlternativeTitles(
    val results: List<TmdbAlternativeTitle>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbAlternativeTitles?): List<TmdbAlternativeTitle> {
                return obj?.results ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbAlternativeTitle>): TmdbAlternativeTitles {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}