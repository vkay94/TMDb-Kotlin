package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.ProductionStatus
import de.vkay.api.tmdb.enumerations.ShowType
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.Rated
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage
import de.vkay.api.tmdb.internals.models.TmdbImages

@JsonClass(generateAdapter = true)
class TmdbShow internal constructor(
    val id: Int,
    @Json(name = "name")
    val title: String,
    @Json(name = "first_air_date")
    val firstAirDate: TmdbDate?,
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
    val latestEpisode: TmdbEpisode.Slim?,
    @Json(name = "next_episode_to_air")
    val nextEpisode: TmdbEpisode.Slim?,
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
    val seasons: List<TmdbSeason.Slim>,
    val tagline: String,
    val type: ShowType,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<TmdbLanguage>,
    @Json(name = "backdrop_path")
    @TMDbImage
    val backdrop: TmdbImage?,
    @Json(name = "poster_path")
    @TMDbImage
    val poster: TmdbImage?,
    @Json(name = "production_companies")
    val productionCompanies: List<TmdbCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<TmdbCountry>,

    // Append
    @Json(name = "images")
    internal val _images: TmdbImages?,
    @Json(name = "recommendations")
    internal val _recommendations: TmdbPage<Slim>?,
    @Json(name = "similar")
    internal val _similar: TmdbPage<Slim>?,
    @Json(name = "external_ids")
    val externalIds: TmdbExternalIds?,
    @ResultsList
    @Json(name = "videos")
    internal val _videos: List<TmdbVideo>?,
    @Json(name = "keywords")
    @ResultsList
    internal val _keywords: List<TmdbKeyword>?,
    @Json(name = "watch/providers")
    @OtherCases
    val watchProviders: Map<String, TmdbWatchProviderListObject>?
) : MediaTypeItem(MediaType.TV) {

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val backdrops: List<TmdbImage> = _images?.backdrops ?: emptyList()
    val posters: List<TmdbImage> = _images?.posters ?: emptyList()
    val logos: List<TmdbImage> = _images?.logos ?: emptyList()

    val recommendations: List<Slim> = _recommendations?.results ?: emptyList()
    val similar: List<Slim> = _similar?.results ?: emptyList()

    val keywords: List<TmdbKeyword> = _keywords ?: emptyList()

    val runtime: Int = episodeRuntimes.firstOrNull() ?: 0
    val hasSpecials: Boolean = seasons.any { it.seasonNumber == 0 }
    val nextAirDate: TmdbDate? = nextEpisode?.airDate

    /**
     * Returns the total number of episodes until now depending on the seasons excluding [nextAirDate]
     */
    val numberOfAiredEpisodes: Int
        get() {
            if (latestEpisode == null) return 0
            val latestSeasonEpisodeCount = seasons
                .first { it.seasonNumber == latestEpisode.seasonNumber }
                .episodeCount

            return if (latestEpisode.episodeNumber <= latestSeasonEpisodeCount)
                // The latest season starts at 1
                (numberOfEpisodes - latestSeasonEpisodeCount) + latestEpisode.episodeNumber
            else
                // The episode numbers don't reset for every season
                latestEpisode.episodeNumber
        }

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val id: Int,
        @Json(name = "name")
        val title: String,
        @Json(name = "first_air_date")
        val firstAirDate: TmdbDate?,
        @Json(name = "backdrop_path")
        @TMDbImage
        val backdrop: TmdbImage?,
        @Json(name = "poster_path")
        @TMDbImage
        val poster: TmdbImage?,
        @Json(name = "original_name")
        val originalTitle: String,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int,
        val popularity: Double,

        // Optional when getting rated shows (AccountService)
        @Rated
        val rating: Int?
    ) : MediaTypeItem(MediaType.TV)
}