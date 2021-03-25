package de.vkay.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.tmdb.models.TmdbErrorResponse
import de.vkay.tmdb.models.TmdbKeyword
import retrofit2.http.GET
import retrofit2.http.Path

interface KeywordService {

    @GET("keyword/{keyword_id}")
    suspend fun details(
        @Path("keyword_id") keywordId: Int
    ): NetworkResponse<TmdbKeyword, TmdbErrorResponse>
}