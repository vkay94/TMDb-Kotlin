package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("person/{person_id}")
    suspend fun details(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null
    ): NetworkResponse<TmdbPerson, TmdbError.DefaultError>

    @GET("person/{person_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("person/{person_id}/images")
    @ResultsList
    suspend fun profiles(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    @GET("person/{person_id}/external_ids")
    suspend fun externalIds(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    @GET("person/popular")
    suspend fun popular(
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbPage<TmdbPerson.Slim>, TmdbError.DefaultError>
}