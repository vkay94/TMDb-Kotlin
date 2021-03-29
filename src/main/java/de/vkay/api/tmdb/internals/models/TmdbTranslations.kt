package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbTranslation

@JsonClass(generateAdapter = true)
internal data class TmdbTranslations(
    val translations: List<TmdbTranslation>
)