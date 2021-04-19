package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbKeyword
import retrofit2.http.GET
import retrofit2.http.Path

interface KeywordService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/keywords/get-keyword-details)
     */
    @GET("keyword/{keyword_id}")
    suspend fun details(
        @Path("keyword_id") keywordId: Int
    ): NetworkResponse<TmdbKeyword, TmdbError.DefaultError>
}