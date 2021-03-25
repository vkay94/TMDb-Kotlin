package de.vkay.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/tv")
    suspend fun tv(
        @Query("query") query: String?,
        @Query("language") language: String? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPageShows, TmdbErrorResponse>

    @GET("search/movie")
    suspend fun movie(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("year") year: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPageMovies, TmdbErrorResponse>

    @GET("search/person")
    suspend fun person(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPagePersons, TmdbErrorResponse>

    @GET("search/multi")
    suspend fun multi(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPageMultiSearch, TmdbErrorResponse>

    @GET("search/keyword")
    suspend fun keyword(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPageKeywords, TmdbErrorResponse>
}