package de.vkay.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.tmdb.models.TmdbEpisodeGroupListObject

@JsonClass(generateAdapter = true)
internal data class TmdbEpisodeGroups(
    val results: List<TmdbEpisodeGroupListObject>
)