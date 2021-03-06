package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.enumerations.ExternalId
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbError
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FindService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/find/find-by-id)
     */
    @GET("find/{external_id}")
    @OtherCases
    suspend fun find(
        @Path("external_id") id: String,
        @Query("external_source") source: ExternalId,
    ): NetworkResponse<MediaTypeItem, TmdbError.DefaultError>
}