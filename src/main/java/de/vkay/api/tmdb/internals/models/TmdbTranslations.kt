package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbTranslation

@JsonClass(generateAdapter = true)
internal data class TmdbTranslations(
    val translations: List<TmdbTranslation>
) {
    companion object {
        internal val ADAPTER = object : Any() {
            @ListMap
            @FromJson
            fun from(obj: TmdbTranslations?): List<TmdbTranslation> {
                return obj?.translations ?: emptyList()
            }

            @ToJson
            fun to(@ListMap list: List<TmdbTranslation>): TmdbTranslations {
                throw NotImplementedError("Not implemented")
            }
        }
    }
}