package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.EpisodeGroupType

@JsonClass(generateAdapter = true)
data class TmdbEpisodeGroupListObject internal constructor(
    val id: String,
    val type: EpisodeGroupType,
    val name: String,
    val description: String,
    @Json(name = "episode_count")
    val episodeCount: Int,
    @Json(name = "group_count")
    val groupCount: Int,
    val groups: List<TmdbEpisodeGroup>?
)