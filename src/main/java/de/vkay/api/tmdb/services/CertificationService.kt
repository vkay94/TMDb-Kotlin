package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.TmdbCertificationList
import de.vkay.api.tmdb.models.TmdbError
import retrofit2.http.GET

interface CertificationService {

    @GET("certification/tv/list")
    suspend fun tv(): NetworkResponse<TmdbCertificationList, TmdbError.DefaultError>

    @GET("certification/movie/list")
    suspend fun movie(): NetworkResponse<TmdbCertificationList, TmdbError.DefaultError>
}