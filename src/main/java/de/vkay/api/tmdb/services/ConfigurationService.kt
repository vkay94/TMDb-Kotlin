package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.TmdbErrorResponse
import de.vkay.api.tmdb.models.TmdbLanguage
import retrofit2.http.GET

interface ConfigurationService {

    @GET("configuration/languages")
    suspend fun languages()
    : NetworkResponse<List<TmdbLanguage>, TmdbErrorResponse>
}