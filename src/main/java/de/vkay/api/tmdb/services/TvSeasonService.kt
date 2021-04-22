package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvSeasonService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-details)
     */
    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null
    ): NetworkResponse<TmdbSeason, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-external-ids)
     */
    @GET("tv/{tv_id}/season/{season_number}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-images)
     */
    @GET("tv/{tv_id}/season/{season_number}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/credits")
    @ResultsList("crew")
    suspend fun crew(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbCredit.Crew>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/credits")
    @ResultsList("cast")
    suspend fun cast(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbCredit.Cast>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-aggregate-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/aggregate_credits")
    @ResultsList("crew")
    suspend fun aggregateCrew(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbCredit.Crew>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-aggregate-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/aggregate_credits")
    @ResultsList("cast")
    suspend fun aggregateCast(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbCredit.Cast>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-translations)
     */
    @GET("tv/{tv_id}/season/{season_number}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-videos)
     */
    @GET("tv/{tv_id}/season/{season_number}/videos")
    @ResultsList
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-seasons/get-tv-season-account-states)
     */
    @GET("tv/{tv_id}/season/{season_number}/account_states")
    @ResultsList("results")
    suspend fun accountState(
        @Path("tv_id") tvId: Int,
        @Path("season_number") season: Int
    ) : NetworkResponse<List<TmdbAccountState.EpisodeItem>, TmdbError.DefaultError>
}