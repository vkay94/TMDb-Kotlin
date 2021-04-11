package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.TmdbAccessTokenResponse
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbMessage
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface AuthService {

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("auth/request_token")
    suspend fun requestToken(): NetworkResponse<TmdbMessage.RequestToken, TmdbError.DefaultError>

    @Headers("Content-Type: application/json;charset=utf-8")
    @FormUrlEncoded
    @POST("auth/access_token")
    suspend fun accessToken(
        @Field("request_token") requestToken: String
    ): NetworkResponse<TmdbAccessTokenResponse, TmdbError.DefaultError>
}