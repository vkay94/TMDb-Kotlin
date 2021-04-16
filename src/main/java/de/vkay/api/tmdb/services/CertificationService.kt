package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.MapList
import de.vkay.api.tmdb.models.TmdbCertification
import de.vkay.api.tmdb.models.TmdbError
import retrofit2.http.GET

interface CertificationService {

    @GET("certification/tv/list")
    @MapList("certifications")
    suspend fun tv(): NetworkResponse<Map<String, List<TmdbCertification>>, TmdbError.DefaultError>

    @GET("certification/movie/list")
    @MapList("certifications")
    suspend fun movie(): NetworkResponse<Map<String, List<TmdbCertification>>, TmdbError.DefaultError>
}