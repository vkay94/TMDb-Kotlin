package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.ResultsList
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
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/similar")
    suspend fun similar(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/images")
    @ResultsList("backdrops")
    suspend fun backdrops(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/videos")
    @ResultsList
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    @GET("tv/{tv_id}/keywords")
    @ResultsList
    suspend fun keywords(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbKeyword>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/content_ratings")
    @ResultsList
    suspend fun contentRatings(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbContentRating>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/alternative_titles")
    @ResultsList
    suspend fun alternativeTitles(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbAlternativeTitle>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/credits")
    @ResultsList("cast")
    suspend fun cast(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbCredit.Cast>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/aggregate_credits")
    @ResultsList("crew")
    suspend fun aggregateCrew(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbCredit.Crew>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/aggregate_credits")
    @ResultsList("cast")
    suspend fun aggregateCast(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbCredit.Cast>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/credits")
    @ResultsList("crew")
    suspend fun crew(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbCredit.Crew>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/watch/providers")
    @OtherCases
    suspend fun watchProviders(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<Map<String, TmdbWatchProviderListObject>, TmdbError.DefaultError>

    @GET("tv/latest")
    suspend fun latest(
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbShow, TmdbError.DefaultError>

    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/popular")
    suspend fun popular(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/top_rated")
    suspend fun topRated(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    @GET("tv/{tv_id}/episode_groups")
    @ResultsList
    suspend fun episodeGroups(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbEpisodeGroupListObject>, TmdbError.DefaultError>

    @GET("tv/episode_group/{id}")
    suspend fun episodeGroupDetails(
        @Path("id") groupId: String,
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbEpisodeGroupListObject, TmdbError.DefaultError>
}