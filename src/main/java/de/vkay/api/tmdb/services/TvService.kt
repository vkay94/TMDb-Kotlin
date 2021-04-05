package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.internals.ListMapParser
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvService {

    @GET("tv/{tv_id}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: ImageLanguages? = null
    ): NetworkResponse<TmdbShow, TmdbError.DefaultError>

    @GET("tv/{tv_id}/recommendations")
    suspend fun recommendations(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShowListObject>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/similar")
    suspend fun similar(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShowListObject>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/images")
    suspend fun images(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<TmdbShow.Images, TmdbError.DefaultError>

    @GET("tv/{tv_id}/videos")
    @ListMapParser
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    @GET("tv/{tv_id}/keywords")
    @ListMapParser
    suspend fun keywords(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbKeyword>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/content_ratings")
    @ListMapParser
    suspend fun contentRatings(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbContentRating>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/alternative_titles")
    @ListMapParser
    suspend fun alternativeTitles(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbAlternativeTitle>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/translations")
    @ListMapParser
    suspend fun translations(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/credits")
    suspend fun credits(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<TmdbShow.Credits, TmdbError.DefaultError>

    @GET("tv/{tv_id}/watch/providers")
    @ListMapParser
    suspend fun watchProviders(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<Map<String, TmdbWatchProviderList>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/episode_groups")
    @ListMapParser
    suspend fun episodeGroups(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbEpisodeGroupListObject>, TmdbError.DefaultError>

    @GET("tv/episode_group/{id}")
    suspend fun episodeGroupDetails(
        @Path("id") groupId: String,
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbEpisodeGroupListObject, TmdbError.DefaultError>
}