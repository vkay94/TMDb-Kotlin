package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbEpisodeGroupListObject

@JsonClass(generateAdapter = true)
internal data class TmdbEpisodeGroups(
    val results: List<TmdbEpisodeGroupListObject>
)