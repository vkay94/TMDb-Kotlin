package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvEpisodeService {

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") appendToResponse: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: ImageLanguages? = null
    ): NetworkResponse<TmdbEpisode, TmdbError.DefaultError>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    suspend fun credits(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<TmdbEpisode.Credits, TmdbError.DefaultError>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/images")
    suspend fun images(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<TmdbEpisode.Images, TmdbError.DefaultError>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/translations")
    @ResultsList
    suspend fun translations(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>
}