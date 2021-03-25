package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.EpisodeGroupType

@JsonClass(generateAdapter = true)
data class TmdbEpisodeGroupListObject(
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