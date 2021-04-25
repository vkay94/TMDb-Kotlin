package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.PersonGender
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

    @Json(name = "images")
    internal val _images: Images?

) : MediaTypeItem(MediaType.PERSON) {

    val profiles: List<TmdbImage> = _images?.profiles ?: emptyList()

    val isDead: Boolean = deathDay != null

    @JsonClass(generateAdapter = true)
    internal data class Images(
        val profiles: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val name: String,
        val id: Int,
        val gender: PersonGender,
        @Json(name = "profile_path")
        @TMDbImage
        val profile: TmdbImage?,
    ) : MediaTypeItem(MediaType.PERSON)

    //region Credits related sub-classes

    data class RoleJob internal constructor(
        val creditId: String,
        val jobCharacter: String,
        val episodeCount: Int?
    )

    data class Cast internal constructor(
        val personId: Int,
        val name: String,
        val originalName: String,
        val gender: PersonGender?,
        val adult: Boolean,
        val profile: TmdbImage?,
        val knownForDepartment: String,
        val roles: List<RoleJob>,
        val totalEpisodeCount: Int?,
        val order: Int
    )

    data class Crew internal constructor(
        val personId: Int,
        val name: String,
        val originalName: String,
        val gender: PersonGender?,
        val adult: Boolean,
        val profile: TmdbImage?,
        val knownForDepartment: String,
        val jobs: List<RoleJob>,
        val totalEpisodeCount: Int?,
        val department: String
    )

    @JsonClass(generateAdapter = true)
    data class Guest internal constructor(
        @Json(name = "id")
        val personId: Int,
        @Json(name = "credit_id")
        val creditId: String,
        val name: String,
        @Json(name = "original_name")
        val originalName: String,
        val gender: PersonGender?,
        val adult: Boolean,
        @Json(name = "profile_path")
        @TMDbImage
        val profile: TmdbImage?,
        @Json(name = "known_for_department")
        val knownForDepartment: String,

        @Json(name = "character")
        val character: String
    )

    //endregion
}