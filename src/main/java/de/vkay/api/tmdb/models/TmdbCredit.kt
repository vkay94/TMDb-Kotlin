package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.PersonGender

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
        internal val _profilePath: String?,
        val knownForDepartment: String,
        val roles: List<RoleJob>,
        val totalEpisodeCount: Int?,
        val order: Int
    ) {
        val profile: TmdbImage?
            get() = if (!_profilePath.isNullOrBlank())
                TmdbImage(_profilePath, 0, 0, null)
            else null
    }

    data class Crew internal constructor(
        val personId: Int,
        val name: String,
        val originalName: String,
        val gender: PersonGender?,
        val adult: Boolean,
        internal val _profilePath: String?,
        val knownForDepartment: String,
        val jobs: List<RoleJob>,
        val totalEpisodeCount: Int?,
        val department: String
    ) {
        val profile: TmdbImage?
            get() = if (!_profilePath.isNullOrBlank())
                TmdbImage(_profilePath, 0, 0, null)
            else null
    }

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
        val _profilePath: String?,
        @Json(name = "known_for_department")
        val knownForDepartment: String,

        @Json(name = "character")
        val character: String
    ) {
        val profile: TmdbImage?
            get() = if (!_profilePath.isNullOrBlank())
                TmdbImage(_profilePath!!, 0, 0, null)
            else null
    }
}
