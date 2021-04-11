package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbGenre
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET("genre/tv/list")
    @ListMap
    suspend fun tv(
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbError.DefaultError>

    @GET("genre/movie/list")
    @ListMap
    suspend fun movie(
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbError.DefaultError>
}