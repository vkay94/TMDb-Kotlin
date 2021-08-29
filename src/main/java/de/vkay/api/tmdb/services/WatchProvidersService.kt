package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbRegion
import de.vkay.api.tmdb.models.TmdbWatchProvider
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchProvidersService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/watch-providers/get-available-regions)
     */
    @GET("watch/providers/regions")
    @ResultsList
    suspend fun regions(
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbRegion>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/watch-providers/get-movie-providers)
     */
    @GET("watch/providers/movie")
    @ResultsList
    suspend fun movie(
        @Query("language") languageTag: String? = null,
        @Query("watch_region") region: String? = null
    ): NetworkResponse<List<TmdbWatchProvider>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/watch-providers/get-tv-providers)
     */
    @GET("watch/providers/tv")
    @ResultsList
    suspend fun tv(
        @Query("language") languageTag: String? = null,
        @Query("watch_region") region: String? = null
    ): NetworkResponse<List<TmdbWatchProvider>, TmdbError.DefaultError>
}