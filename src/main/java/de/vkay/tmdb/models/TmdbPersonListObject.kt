package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType
import de.vkay.tmdb.enumerations.PersonGender
import de.vkay.tmdb.enumerations.SearchMedia

@JsonClass(generateAdapter = true)
data class TmdbPersonListObject(
    val name: String,
    val id: Int,
    val gender: PersonGender,
    @Json(name = "profile_path")
    internal val _profilePath: String?
) : SearchMediaItem(SearchMedia.PERSON), MediaItem {

    override val mediaType: MediaType = MediaType.PERSON

    val profile: TmdbImage?
        get() = if (!_profilePath.isNullOrBlank())
            TmdbImage(_profilePath)
        else null
}