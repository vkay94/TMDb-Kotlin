package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbExternalIds
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbTranslation
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
    @ListMapParser
    suspend fun translations(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("person/{person_id}/images")
    suspend fun images(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbPerson.Images, TmdbError.DefaultError>

    @GET("person/{person_id}/external_ids")
    suspend fun externalIds(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>
}