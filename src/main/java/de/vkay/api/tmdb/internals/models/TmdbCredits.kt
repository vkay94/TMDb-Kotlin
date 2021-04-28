package de.vkay.api.tmdb.internals.models

import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.models.TmdbPerson

@JsonClass(generateAdapter = true)
internal class TmdbCredits internal constructor(
    val cast: List<TmdbPerson.Cast>?,
    val crew: List<TmdbPerson.Crew>?,
    val guest_stars: List<TmdbPerson.Guest>?
)