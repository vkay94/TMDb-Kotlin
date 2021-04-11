package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ListMap
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("network/{network_id}")
    suspend fun details(
        @Path("network_id") id: Int
    ) : NetworkResponse<TmdbNetwork, TmdbError.DefaultError>

    @GET("network/{network_id}/images")
    @ListMap
    suspend fun logos(
        @Path("network_id") id: Int
    ) : NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>
}