package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.ProductionStatus
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.Rated
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.internals.annotations.TMDbImage
import de.vkay.api.tmdb.internals.models.TmdbImages

@JsonClass(generateAdapter = true)
data class TmdbMovie internal constructor(
    val id: Int,
    val title: String,
    @Json(name = "backdrop_path")
    @TMDbImage
    val backdrop: TmdbImage?,
    @Json(name = "poster_path")
    @TMDbImage
    val poster: TmdbImage?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val genres: List<TmdbGenre>,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: TmdbDate?,
    val adult: Boolean,
    val popularity: Float,
    @Json(name = "vote_count")
    val voteCount: Long,
    val video: Boolean,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: TmdbCollection.Slim?,
    val budget: Long,
    val revenue: Long,
    val runtime: Int,
    val homepage: String,
    @Json(name = "imdb_id")
    val imdbId: String?,
    val status: ProductionStatus,
    val tagline: String,
    @Json(name = "production_companies")
    val productionCompanies: List<TmdbCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<TmdbCountry>,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<TmdbLanguage>,

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
    @ResultsList("keywords")
    internal val _keywords: List<TmdbKeyword>?,
    @Json(name = "watch/providers")
    @OtherCases
    val watchProviders: Map<String, TmdbWatchProviderListObject>?
) : MediaTypeItem(MediaType.MOVIE) {

    val videos: List<TmdbVideo> = _videos ?: emptyList()
    val backdrops: List<TmdbImage> = _images?.backdrops ?: emptyList()
    val posters: List<TmdbImage> = _images?.posters ?: emptyList()
    val logos: List<TmdbImage> = _images?.logos ?: emptyList()

    val recommendations: List<Slim> = _recommendations?.results ?: emptyList()
    val similar: List<Slim> = _similar?.results ?: emptyList()

    val keywords: List<TmdbKeyword> = _keywords ?: emptyList()

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val id: Int,
        val title: String,
        @Json(name = "backdrop_path")
        @TMDbImage
        val backdrop: TmdbImage?,
        @Json(name = "poster_path")
        @TMDbImage
        val poster: TmdbImage?,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "vote_average")
        val voteAverage: Double,
        val overview: String,
        @Json(name = "release_date")
        val releaseDate: TmdbDate?,
        val adult: Boolean,
        val popularity: Double,
        @Json(name = "vote_count")
        val voteCount: Int,
        val video: Boolean,

        // Optional when getting rated movies (AccountService)
        @Rated
        val rating: Int?
    ) : MediaTypeItem(MediaType.MOVIE)
}