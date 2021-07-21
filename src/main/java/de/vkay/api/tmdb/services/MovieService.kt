package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.CharJob
import de.vkay.api.tmdb.internals.annotations.MapList
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-details)
     */
    @GET("movie/{movie_id}")
    suspend fun details(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: ImageLanguages? = null
    ): NetworkResponse<TmdbMovie, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-alternative-titles)
     */
    @GET("movie/{movie_id}/alternative_titles")
    @ResultsList("titles")
    suspend fun alternativeTitles(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbAlternativeTitle>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-credits)
     */
    @GET("movie/{movie_id}/credits")
    @CharJob(fieldName = "cast", mediaType = MediaType.PERSON)
    suspend fun cast(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-credits)
     */
    @GET("movie/{movie_id}/credits")
    @CharJob(fieldName = "crew", mediaType = MediaType.PERSON)
    suspend fun crew(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-external-ids)
     */
    @GET("movie/{movie_id}/external_ids")
    suspend fun externalIds(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-images)
     */
    @GET("movie/{movie_id}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-images)
     */
    @GET("movie/{movie_id}/images")
    @ResultsList("backdrops")
    suspend fun backdrops(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-recommendations)
     */
    @GET("movie/{movie_id}/recommendations")
    suspend fun recommendations(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-similar-movies)
     */
    @GET("movie/{movie_id}/similar")
    suspend fun similar(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-videos)
     */
    @GET("movie/{movie_id}/videos")
    @ResultsList
    suspend fun videos(
        @Path("movie_id") movieId: Int,
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-keywords)
     */
    @GET("movie/{movie_id}/keywords")
    @ResultsList("keywords")
    suspend fun keywords(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbKeyword>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-translations)
     */
    @GET("movie/{movie_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-watch-providers)
     *
     * **Map key**: Country code, for example: DE, US, FR
     */
    @GET("movie/{movie_id}}/watch/providers")
    @OtherCases
    suspend fun watchProviders(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<Map<String, TmdbWatchProviderListObject>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-lists)
     */
    @GET("movie/{movie_id}/lists")
    suspend fun lists(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<TmdbPage<TmdbList>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-release-dates)
     *
     * **Map key**: Country code, for example: DE, US, FR
     */
    @GET("movie/{movie_id}/release_dates")
    @MapList
    suspend fun releaseDates(
        @Path("movie_id") movieId: Int
    ): NetworkResponse<Map<String, List<TmdbReleaseDate>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-latest-movie)
     */
    @GET("movie/latest")
    suspend fun latest(): NetworkResponse<TmdbMovie, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-now-playing)
     */
    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("language") languageTag: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-popular-movies)
     */
    @GET("movie/popular")
    suspend fun popular(
        @Query("language") languageTag: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-top-rated-movies)
     */
    @GET("movie/top_rated")
    suspend fun topRated(
        @Query("language") languageTag: String? = null,
        @Query("region") languageCode: String?,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-upcoming)
     */
    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("language") languageTag: String? = null,
        @Query("region") languageCode: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/movies/get-movie-account-states)
     */
    @GET("movie/{movie_id}/account_states")
    suspend fun accountState(
        @Path("movie_id") movieId: Int
    ) : NetworkResponse<TmdbAccountState.Movie, TmdbError>
}