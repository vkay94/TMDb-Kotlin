package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage

@JsonClass(generateAdapter = true)
data class TmdbPerson internal constructor(
    val name: String,
    val id: Int,
    val gender: PersonGender,
    val biography: String,
    @Json(name = "deathday")
    val deathDay: TmdbDate?,
    @Json(name = "birthday")
    val birthDay: TmdbDate?,
    @Json(name = "place_of_birth")
    val birthPlace: String?,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "profile_path")
    @TMDbImage
    val profile: TmdbImage?,

    // Append
    @Json(name = "images")
    @ResultsList("profiles")
    internal val _profiles: List<TmdbImage>?

) : MediaTypeItem(MediaType.PERSON) {

    val profiles: List<TmdbImage> = _profiles ?: emptyList()

    val isDead: Boolean = deathDay != null

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val adult: Boolean,
        val name: String,
        val id: Int,
        val gender: PersonGender,
        @Json(name = "profile_path")
        @TMDbImage
        val profile: TmdbImage?,
        val popularity: Double,
        @Json(name = "known_for_department")
        val knownForDepartment: String,

        // Optional (not in Find and Search)
        @Json(name = "original_name")
        val originalName: String?
    ) : MediaTypeItem(MediaType.PERSON)

    //region Credits related sub-classes

    data class CrewJob internal constructor(
        val creditId: String,
        val job: String,
        val department: String,

        // Optional TV
        val episodeCount: Int? = null,
    )

    data class CastRole internal constructor(
        val creditId: String,
        val character: String,
        // Optional
        val order: Int = -1,
        // Aggregated credits and person tv credits
        val episodeCount: Int? = null
    )

    //endregion
}