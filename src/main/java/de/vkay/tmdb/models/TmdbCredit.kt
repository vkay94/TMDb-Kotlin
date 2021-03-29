package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.PersonGender

sealed class TmdbCredit {
    abstract val personId: Int // id
    abstract val creditId: String

    abstract val name: String
    abstract val originalName: String
    abstract val gender: PersonGender?
    abstract val adult: Boolean
    abstract val knownForDepartment: String

    internal abstract val _profilePath: String?

    val profile: TmdbImage?
        get() = if (!_profilePath.isNullOrBlank())
            TmdbImage(_profilePath!!, 0, 0, null)
        else null

    @JsonClass(generateAdapter = true)
    data class Cast internal constructor(
        @Json(name = "id")
        override val personId: Int,
        @Json(name = "credit_id")
        override val creditId: String,
        override val name: String,
        @Json(name = "original_name")
        override val originalName: String,
        override val gender: PersonGender?,
        override val adult: Boolean,
        @Json(name = "profile_path")
        override val _profilePath: String?,
        @Json(name = "known_for_department")
        override val knownForDepartment: String,

        val character: String
    ) : TmdbCredit()

    @JsonClass(generateAdapter = true)
    data class Crew internal constructor(
        @Json(name = "id")
        override val personId: Int,
        @Json(name = "credit_id")
        override val creditId: String,
        override val name: String,
        @Json(name = "original_name")
        override val originalName: String,
        override val gender: PersonGender?,
        override val adult: Boolean,
        @Json(name = "profile_path")
        override val _profilePath: String?,
        @Json(name = "known_for_department")
        override val knownForDepartment: String,

        val job: String
    ) : TmdbCredit()

    @JsonClass(generateAdapter = true)
    data class Guest internal constructor(
        @Json(name = "id")
        override val personId: Int,
        @Json(name = "credit_id")
        override val creditId: String,
        override val name: String,
        @Json(name = "original_name")
        override val originalName: String,
        override val gender: PersonGender?,
        override val adult: Boolean,
        @Json(name = "profile_path")
        override val _profilePath: String?,
        @Json(name = "known_for_department")
        override val knownForDepartment: String,

        @Json(name = "character")
        val character: String
    ) : TmdbCredit()
}
