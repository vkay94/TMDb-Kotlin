package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.PersonGender

@JsonClass(generateAdapter = true)
data class TmdbPersonListObject internal constructor(
    val name: String,
    val id: Int,
    val gender: PersonGender,
    @Json(name = "profile_path")
    internal val _profilePath: String?
) : MediaTypeItem(MediaType.PERSON) {

    val profile: TmdbImage?
        get() = if (!_profilePath.isNullOrBlank())
            TmdbImage(_profilePath)
        else null
}