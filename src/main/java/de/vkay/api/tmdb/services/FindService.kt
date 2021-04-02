package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.enumerations.ExternalId
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbErrorResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FindService {

    @GET("find/{external_id}")
    @ListMapParser
    suspend fun find(
        @Path("external_id") id: String,
        @Query("external_source") source: ExternalId,
    ): NetworkResponse<MediaTypeItem, TmdbErrorResponse>
}