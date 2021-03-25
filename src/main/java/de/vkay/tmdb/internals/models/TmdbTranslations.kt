package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbTranslation

@JsonClass(generateAdapter = true)
internal data class TmdbTranslations(
    val translations: List<TmdbTranslation>
)