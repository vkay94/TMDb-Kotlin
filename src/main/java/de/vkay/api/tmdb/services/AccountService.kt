package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.models.*
import retrofit2.http.*

interface AccountService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/authentication/create-session-from-v4-access-token)
     */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("authentication/session/convert/4")
    suspend fun accessTokenToSessionID(
        @Field("access_token") id: String
    ) : NetworkResponse<TmdbMessage.SessionId, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-account-details)
     */
    @GET("account")
    suspend fun details() : NetworkResponse<TmdbAccount, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-created-lists)
     */
    @GET("account/{account_id}/lists")
    suspend fun createdLists(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
    ) : NetworkResponse<TmdbPage<TmdbList.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-favorite-movies)
     */
    @GET("account/{account_id}/favorite/movies")
    suspend fun favoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-favorite-tv-shows)
     */
    @GET("account/{account_id}/favorite/tv")
    suspend fun favoriteShows(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-movie-watchlist)
     */
    @GET("account/{account_id}/watchlist/movies")
    suspend fun watchlistMovies(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-tv-show-watchlist)
     */
    @GET("account/{account_id}/watchlist/tv")
    suspend fun watchlistShows(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-rated-movies)
     */
    @GET("account/{account_id}/rated/movies")
    suspend fun ratedMovies(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbMovie.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-rated-tv-shows)
     */
    @GET("account/{account_id}/rated/tv")
    suspend fun ratedShows(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/get-rated-tv-episodes)
     */
    @GET("account/{account_id}/rated/tv/episodes")
    suspend fun ratedEpisodes(
        @Path("account_id") accountId: Int,
        @Query("language") languageTag: String? = null,
        @Query("page") page: Int? = null
        // TODO: SortBy (created_at.asc, created_at.desc)
    ) : NetworkResponse<TmdbPage<TmdbEpisode.Slim>, TmdbError.DefaultError>


    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/mark-as-favorite)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("account/{account_id}/favorite")
    suspend fun markFavorite(
        @Path("account_id") accountId: Int,
        @Body body: TmdbBody.MarkFavorite
    ) : NetworkResponse<TmdbMessage, TmdbError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/account/add-to-watchlist)
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: Int,
        @Body body: TmdbBody.AddToWatchlist
    ) : NetworkResponse<TmdbMessage, TmdbError>
}