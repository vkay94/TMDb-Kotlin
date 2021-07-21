package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbGenre
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/genres/get-tv-list)
     */
    @GET("genre/tv/list")
    @ResultsList("genres")
    suspend fun tv(
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/genres/get-movie-list)
     */
    @GET("genre/movie/list")
    @ResultsList("genres")
    suspend fun movie(
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbError.DefaultError>
}