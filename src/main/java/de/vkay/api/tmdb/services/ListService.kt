package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.TmdbErrorResponse
import de.vkay.api.tmdb.models.TmdbList
import retrofit2.http.GET
import retrofit2.http.Path

interface ListService {

    @GET("list/{list_id}")
    suspend fun details(
        @Path("list_id") id: Int
    ): NetworkResponse<TmdbList, TmdbErrorResponse>
}