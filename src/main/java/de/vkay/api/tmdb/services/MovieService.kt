package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{movie_id}")
    suspend fun details(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: ImageLanguages? = null
    ): NetworkResponse<TmdbMovie, TmdbError.DefaultError>

    @GET("movie/{movie_id}/alternative_titles")
    @ResultsList("titles")
    suspend fun alternativeTitles(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbAlternativeTitle>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/credits")
    @ResultsList("cast")
    suspend fun cast(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbCredit.Cast>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/credits")
    @ResultsList("crew")
    suspend fun crew(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbCredit.Crew>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/external_ids")
    suspend fun externalIds(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    @GET("movie/{movie_id}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/images")
    @ResultsList("backdrops")
    suspend fun backgrounds(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendations(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/videos")
    @ResultsList
    suspend fun videos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/keywords")
    @ResultsList("keywords")
    suspend fun keywords(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbKeyword>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("movie/{movie_id}}/watch/providers")
    @OtherCases
    suspend fun watchProviders(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<Map<String, TmdbWatchProviderListObject>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/lists")
    suspend fun lists(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbPage<TmdbList>, TmdbError.DefaultError>

    @GET("movie/{movie_id}/release_dates")
    suspend fun releaseDates(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<Map<String, List<TmdbReleaseDate>>, TmdbError.DefaultError>

    @GET("movie/latest")
    suspend fun latest(): NetworkResponse<TmdbMovie, TmdbError.DefaultError>

    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("language") language: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    @GET("movie/popular")
    suspend fun popular(
        @Query("language") language: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    @GET("movie/top_rated")
    suspend fun topRated(
        @Query("language") language: String? = null,
        @Query("region") languageCode: String?,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("language") language: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>
}