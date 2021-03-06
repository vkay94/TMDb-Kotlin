package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbCompany
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbImage
import retrofit2.http.GET
import retrofit2.http.Path

interface CompanyService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/companies/get-company-details)
     */
    @GET("company/{company_id}")
    suspend fun details(
        @Path("company_id") id: Int
    ) : NetworkResponse<TmdbCompany, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/companies/get-company-images)
     */
    @GET("company/{company_id}/images")
    @ResultsList("logos")
    suspend fun logos(
        @Path("company_id") id: Int
    ) : NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>
}