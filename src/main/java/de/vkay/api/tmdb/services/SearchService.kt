package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/search-tv-shows)
     */
    @GET("search/tv")
    suspend fun tv(
        @Query("query") query: String?,
        @Query("language") language: String? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/search-movies)
     */
    @GET("search/movie")
    suspend fun movie(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("year") year: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/search-people)
     */
    @GET("search/person")
    suspend fun person(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbPerson.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/multi-search)
     */
    @GET("search/multi")
    suspend fun multi(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<MediaTypeItem>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/search-keywords)
     */
    @GET("search/keyword")
    suspend fun keyword(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbKeyword>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/search/search-companies)
     */
    @GET("search/company")
    suspend fun company(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbCompany>, TmdbError.DefaultError>
}