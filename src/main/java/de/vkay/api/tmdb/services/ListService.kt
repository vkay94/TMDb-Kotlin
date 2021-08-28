package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbBody
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbList
import de.vkay.api.tmdb.models.TmdbMessage
import retrofit2.http.*

interface ListService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/get-list)
     */
    @GET("list/{list_id}")
    suspend fun details(
        @Path("list_id") id: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null,
        @Query("sort_by") sortBy: ListSortBy? = null
    ): NetworkResponse<TmdbList, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/create-list)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("list")
    suspend fun create(
        @Body body: TmdbBody.CreateList
    ): NetworkResponse<TmdbMessage.CreateList, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/update-list)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @PUT("list/{list_id}")
    suspend fun update(
        @Path("list_id") id: Int,
        @Body body: TmdbBody.UpdateList
    ): NetworkResponse<TmdbMessage, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/add-items)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("list/{list_id}/items")
    @ResultsList
    suspend fun addItems(
        @Path("list_id") id: Int,
        @Body body: TmdbBody.MediaItem.Builder
    ): NetworkResponse<List<TmdbBody.MediaItem>, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/update-items)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @HTTP(method = "PUT", path = "list/{list_id}/items", hasBody = true)
    @ResultsList
    suspend fun updateItems(
        @Path("list_id") id: Int,
        @Body body: TmdbBody.MediaItem.Builder
    ): NetworkResponse<List<TmdbBody.MediaItem>, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/remove-items)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @HTTP(method = "DELETE", path = "list/{list_id}/items", hasBody = true)
    @ResultsList
    suspend fun removeItems(
        @Path("list_id") id: Int,
        @Body body: TmdbBody.MediaItem.Builder
    ): NetworkResponse<List<TmdbBody.MediaItem>, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/clear-list)
     */
    @GET("list/{list_id}/clear")
    suspend fun clear(
        @Path("list_id") id: Int
    ): NetworkResponse<TmdbMessage.ClearList, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/4/list/delete-list)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @DELETE("list/{list_id}")
    suspend fun delete(
        @Path("list_id") id: Int
    ): NetworkResponse<TmdbMessage, TmdbError.DefaultError>
}