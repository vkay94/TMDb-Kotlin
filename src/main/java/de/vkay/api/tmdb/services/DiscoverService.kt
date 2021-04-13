package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.Discover
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPage
import de.vkay.api.tmdb.models.TmdbShow
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DiscoverService {

    @GET("discover/tv")
    suspend fun tv(
        @QueryMap options: Discover.ShowBuilder,
        @Query("language") language: String? = null,
        @Query("page") page: Int = 1
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("discover/movie")
    suspend fun movie(
        @QueryMap options: Discover.MovieBuilder,
        @Query("language") language: String? = null,
        @Query("page") page: Int = 1
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>
}