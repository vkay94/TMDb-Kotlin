package de.vkay.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.tmdb.enumerations.MediaType
import de.vkay.tmdb.enumerations.ProductionStatus
import de.vkay.tmdb.enumerations.ShowType
import de.vkay.tmdb.internals.ListMapParser

@JsonClass(generateAdapter = true)
data class TmdbShow(
    @Json(name = "backdrop_path")
    internal val _backgroundPath: String?,
    val id: Int,
    @Json(name = "name")
    val title: String,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "first_air_date")
    val releaseDate: TmdbDate?,

    @Json(name = "episode_run_time")
    val episodeRuntimes: List<Int>,
    val genres: List<TmdbGenre>,
    val homepage: String,
    @Json(name = "in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @Json(name = "last_air_date")
    val latestAirDate: TmdbDate?,
    @Json(name = "last_episode_to_air")
    val latestEpisode: TmdbEpisode?,
    @Json(name = "next_episode_to_air")
    val nextEpisode: TmdbEpisode?,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int,
    @Json(name = "status")
    val currentStatus: ProductionStatus,
    @Json(name = "original_name")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "origin_country")
    val originalCountries: List<String>,
    val overview: String,

    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    val seasons: List<TmdbSeasonListObject>,
    val tagline: String,
    val type: ShowType,

    @Json(name = "spoken_languages")
    val spokenLanguages: List<TmdbLanguage>,

    // Append
    @Json(name = "images")
    internal val _images: Images?,
    @Json(name = "recommendations")
    internal val _recommendations: TmdbPage<TmdbShowListObject>?,
    @Json(name = "similar")
    internal val _similar: TmdbPage<TmdbShowListObject>?,
    @Json(name = "external_ids")
    val externalIds: TmdbExternalIds?,
    @Json(name = "videos")
    @ListMapParser
    internal val _videos: List<TmdbVideo>?,
    @Json(name = "credits")
    internal val _credits: Credits?,
    @Json(name = "keywords")
    @ListMapParser
    internal val _keywords: List<TmdbKeyword>?

) : MediaTypeItem(MediaType.TV) {

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val backgrounds: List<TmdbImage> = _images?.backdrops ?: emptyList()
    val posters: List<TmdbImage> = _images?.posters ?: emptyList()

    val recommendations: List<TmdbShowListObject> = _recommendations?.results ?: emptyList()
    val similar: List<TmdbShowListObject> = _similar?.results ?: emptyList()

    val cast: List<TmdbCredit.Cast> = _credits?.cast ?: emptyList()
    val crew: List<TmdbCredit.Crew> = _credits?.crew ?: emptyList()
    val keywords: List<TmdbKeyword> = _keywords ?: emptyList()

    val background: TmdbImage?
        get() = if (!_backgroundPath.isNullOrBlank())
            TmdbImage(_backgroundPath)
        else null

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null

    val runtime: Int = episodeRuntimes.firstOrNull() ?: 0
    val hasSpecials: Boolean = seasons.any { it.seasonNumber == 0 }
    val nextAirDate: TmdbDate? = nextEpisode?.releaseDate

    /**
     * Returns the total number of episodes until now depending on the seasons excluding [nextAirDate]
     */
    val numberOfAiredEpisodes: Int
        get() {
            if (latestEpisode == null) return 0
            return latestEpisode.episodeNumber +
                    seasons.filter { it.seasonNumber in 1 until latestEpisode.seasonNumber }
                        .map { it.episodeCount }.sum()
        }


    @JsonClass(generateAdapter = true)
    data class Images(
        val backdrops: List<TmdbImage>,
        val posters: List<TmdbImage>
    )

    @JsonClass(generateAdapter = true)
    data class Credits(
        val cast: List<TmdbCredit.Cast>,
        val crew: List<TmdbCredit.Crew>,
    )
}