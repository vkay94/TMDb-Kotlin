package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbErrorResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface AuthService {

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("auth/request_token")
    @ListMapParser
    suspend fun requestToken(): NetworkResponse<String, TmdbErrorResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @FormUrlEncoded
    @POST("auth/access_token")
    @ListMapParser
    suspend fun accessToken(
        @Field("request_token") requestToken: String
    ): NetworkResponse<String, TmdbErrorResponse>
}