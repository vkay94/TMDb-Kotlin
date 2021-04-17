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
}