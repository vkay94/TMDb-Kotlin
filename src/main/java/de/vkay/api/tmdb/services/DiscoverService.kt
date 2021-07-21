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

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/discover/tv-discover)
     */
    @GET("discover/tv")
    suspend fun tv(
        @QueryMap options: Discover.ShowBuilder,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int = 1
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/discover/movie-discover)
     */
    @GET("discover/movie")
    suspend fun movie(
        @QueryMap options: Discover.MovieBuilder,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int = 1
    ): NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>
}