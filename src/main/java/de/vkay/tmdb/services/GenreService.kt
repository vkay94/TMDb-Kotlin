package de.vkay.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.models.TmdbErrorResponse
import de.vkay.tmdb.models.TmdbGenre
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET("genre/tv/list")
    @ListMapParser
    suspend fun tv(
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbErrorResponse>

    @GET("genre/movie/list")
    @ListMapParser
    suspend fun movie(
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbGenre>, TmdbErrorResponse>
}