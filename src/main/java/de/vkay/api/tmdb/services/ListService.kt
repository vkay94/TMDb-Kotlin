package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.internals.auth.TmdbListCreateResponse
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbList
import retrofit2.http.*

interface ListService {

    @GET("list/{list_id}")
    suspend fun details(
        @Path("list_id") id: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
        @Query("sort_by") sortBy: ListSortBy? = null
    ): NetworkResponse<TmdbList, TmdbError.DefaultError>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("list")
    suspend fun create(
        @Field("name") name: String,
        @Field("iso_639_1") languageCode: String,
        @Field("description") description: String? = null,
        @Field("public") public: Boolean? = null,
        @Field("iso_3166_1") countryCode: String? = null
    ): NetworkResponse<TmdbListCreateResponse, TmdbError>

    @Headers("Content-Type: application/json;charset=utf-8")
    @DELETE("list/{list_id}")
    suspend fun delete(
        @Path("list_id") id: Int
    ): NetworkResponse<Boolean, TmdbError.DefaultError>
}