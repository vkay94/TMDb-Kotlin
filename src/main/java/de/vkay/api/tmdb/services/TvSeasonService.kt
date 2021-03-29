package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvSeasonService {

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null
    ): NetworkResponse<TmdbSeason, TmdbErrorResponse>

    @GET("tv/{tv_id}/season/{season_number}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<TmdbExternalIds, TmdbErrorResponse>

    @GET("tv/{tv_id}/season/{season_number}/images")
    suspend fun images(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<TmdbSeason.Images, TmdbErrorResponse>

    @GET("tv/{tv_id}/season/{season_number}/credits")
    suspend fun credits(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<TmdbSeason.Credits, TmdbErrorResponse>

    @GET("tv/{tv_id}/season/{season_number}/translations")
    @ListMapParser
    suspend fun translations(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbTranslation>, TmdbErrorResponse>

    @GET("tv/{tv_id}/season/{season_number}/videos")
    @ListMapParser
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
    ): NetworkResponse<List<TmdbVideo>, TmdbErrorResponse>
}