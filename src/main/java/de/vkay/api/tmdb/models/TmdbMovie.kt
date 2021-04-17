package de.vkay.api.tmdb.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.ProductionStatus

@JsonClass(generateAdapter = true)
data class TmdbMovie internal constructor(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    internal val _posterPath: String?,
    @Json(name = "backdrop_path")
    internal val _backdropPath: String?,
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
    val belongsToCollection: TmdbCollection.BelongsTo?,
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
    val spokenLanguages: List<TmdbLanguage>
) : MediaTypeItem(MediaType.MOVIE) {

    val backdrop: TmdbImage?
        get() = if (!_backdropPath.isNullOrBlank())
            TmdbImage(_backdropPath)
        else null

    val poster: TmdbImage?
        get() = if (!_posterPath.isNullOrBlank())
            TmdbImage(_posterPath)
        else null

    @JsonClass(generateAdapter = true)
    data class Slim internal constructor(
        val id: Int,
        val title: String,
        @Json(name = "poster_path")
        internal val _posterPath: String?,
        @Json(name = "backdrop_path")
        internal val _backdropPath: String?,
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
        val video: Boolean
    ) : MediaTypeItem(MediaType.MOVIE) {

        val backdrop: TmdbImage?
            get() = if (!_backdropPath.isNullOrBlank())
                TmdbImage(_backdropPath)
            else null

        val poster: TmdbImage?
            get() = if (!_posterPath.isNullOrBlank())
                TmdbImage(_posterPath)
            else null
    }
}