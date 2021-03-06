package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.IncludeLanguages
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.CharJob
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvEpisodeService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-details)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int,
        @Query("language") languageTag: String? = null,
        @Query("append_to_response") appendToResponse: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: IncludeLanguages? = null,
        @Query("include_video_language") videoLanguages: IncludeLanguages? = null
    ): NetworkResponse<TmdbEpisode, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    @CharJob(fieldName = "cast", mediaType = MediaType.PERSON)
    suspend fun cast(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    @CharJob(fieldName = "crew", mediaType = MediaType.PERSON)
    suspend fun crew(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-credits)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/credits")
    @CharJob(fieldName = "guest_stars", mediaType = MediaType.PERSON)
    suspend fun guestStars(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-images)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/images")
    @ResultsList("stills")
    suspend fun stills(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-translations)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-external-ids)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-videos)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/videos")
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Path("season_number") tvShowSeasonNumber: Int,
        @Path("episode_number") tvShowEpisodeNumber: Int,
        @Query("include_video_language") includeLanguages: IncludeLanguages? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episodes/get-tv-episode-account-states)
     */
    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/account_states")
    suspend fun accountState(
        @Path("tv_id") tvId: Int,
        @Path("season_number") season: Int,
        @Path("episode_number") episode: Int
    ) : NetworkResponse<TmdbAccountState.Episode, TmdbError>
}