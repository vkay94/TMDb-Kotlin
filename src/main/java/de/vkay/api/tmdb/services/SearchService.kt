package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/tv")
    suspend fun tv(
        @Query("query") query: String?,
        @Query("language") language: String? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShowListObject>, TmdbError.DefaultError>

    @GET("search/movie")
    suspend fun movie(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("year") year: Int? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbMovieListObject>, TmdbError.DefaultError>

    @GET("search/person")
    suspend fun person(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbPersonListObject>, TmdbError.DefaultError>

    @GET("search/multi")
    suspend fun multi(
        @Query("query") query: String,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<MediaTypeItem>, TmdbError.DefaultError>

    @GET("search/keyword")
    suspend fun keyword(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbKeyword>, TmdbError.DefaultError>

    @GET("search/company")
    suspend fun company(
        @Query("query") query: String,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbCompany>, TmdbError.DefaultError>
}