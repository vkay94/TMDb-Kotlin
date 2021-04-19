package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.enumerations.Trending
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbPage
import retrofit2.http.GET
import retrofit2.http.Path

interface TrendingService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/trending/get-trending)
     */
    @GET("trending/{media_type}/{time_window}")
    suspend fun get(
        @Path("media_type") mediaType: Trending.Type,
        @Path("time_window") timeWindow: Trending.TimeWindow,
    ): NetworkResponse<TmdbPage<MediaTypeItem>, TmdbError.DefaultError>
}