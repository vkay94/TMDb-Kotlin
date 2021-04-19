package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbCollection
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbImage
import de.vkay.api.tmdb.models.TmdbTranslation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/collections/get-collection-details)
     */
    @GET("collection/{collection_id}")
    suspend fun details(
        @Path("collection_id") id: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<TmdbCollection, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/collections/get-collection-images)
     */
    @GET("collection/{collection_id}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("collection_id") id: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/collections/get-collection-images)
     */
    @GET("collection/{collection_id}/images")
    @ResultsList("backdrops")
    suspend fun backdrops(
        @Path("collection_id") id: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/collections/get-collection-translations)
     */
    @GET("collection/{collection_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("collection_id") id: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>
}