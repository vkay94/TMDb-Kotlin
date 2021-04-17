package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.internals.annotations.TMDbImage

class TmdbCredit {

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
}
