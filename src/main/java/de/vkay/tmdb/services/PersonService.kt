package de.vkay.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.tmdb.AppendToResponse
import de.vkay.tmdb.internals.ListMapParser
import de.vkay.tmdb.models.TmdbErrorResponse
import de.vkay.tmdb.models.TmdbExternalIds
import de.vkay.tmdb.models.TmdbPerson
import de.vkay.tmdb.models.TmdbTranslation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET("person/{person_id}")
    suspend fun details(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null
    ): NetworkResponse<TmdbPerson, TmdbErrorResponse>

    @GET("person/{person_id}/translations")
    @ListMapParser
    suspend fun translations(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbErrorResponse>

    @GET("person/{person_id}/images")
    suspend fun images(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbPerson.Images, TmdbErrorResponse>

    @GET("person/{person_id}/external_ids")
    suspend fun externalIds(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbErrorResponse>
}