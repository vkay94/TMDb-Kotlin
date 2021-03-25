package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType
import de.vkay.tmdb.enumerations.PersonGender

@JsonClass(generateAdapter = true)
data class TmdbPerson(
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
    internal val _profilePath: String?,

    @Json(name = "images")
    internal val _images: Images?

) : MediaItem {

    override val mediaType: MediaType = MediaType.PERSON
    val profiles: List<TmdbImage> = _images?.profiles ?: emptyList()

    val profile: TmdbImage?
        get() = if (!_profilePath.isNullOrBlank())
            TmdbImage(_profilePath, 0, 0, null)
        else null

    val isDead: Boolean = deathDay != null

    @JsonClass(generateAdapter = true)
    data class Images(
        val profiles: List<TmdbImage>
    )
}