package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbErrorResponse
import retrofit2.http.Headers
import retrofit2.http.POST


interface AuthService {

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("auth/request_token")
    @ListMapParser
    suspend fun requestToken(): NetworkResponse<String, TmdbErrorResponse>
}