package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.ImageLanguages
import de.vkay.api.tmdb.internals.annotations.CharJobPerson
import de.vkay.api.tmdb.internals.annotations.OtherCases
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-details)
     */
    @GET("tv/{tv_id}")
    suspend fun details(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null,
        @Query("include_image_language") imageLanguages: ImageLanguages? = null
    ): NetworkResponse<TmdbShow, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-recommendations)
     */
    @GET("tv/{tv_id}/recommendations")
    suspend fun recommendations(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-similar-tv-shows)
     */
    @GET("tv/{tv_id}/similar")
    suspend fun similar(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-images)
     */
    @GET("tv/{tv_id}/images")
    @ResultsList("posters")
    suspend fun posters(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-images)
     */
    @GET("tv/{tv_id}/images")
    @ResultsList("backdrops")
    suspend fun backdrops(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-videos)
     */
    @GET("tv/{tv_id}/videos")
    @ResultsList
    suspend fun videos(
        @Path("tv_id") tvShowId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<TmdbVideo>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-external-ids)
     */
    @GET("tv/{tv_id}/external_ids")
    suspend fun externalIds(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-keywords)
     */
    @GET("tv/{tv_id}/keywords")
    @ResultsList
    suspend fun keywords(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbKeyword>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-content-ratings)
     */
    @GET("tv/{tv_id}/content_ratings")
    @ResultsList
    suspend fun contentRatings(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbContentRating>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-alternative-titles)
     */
    @GET("tv/{tv_id}/alternative_titles")
    @ResultsList
    suspend fun alternativeTitles(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbAlternativeTitle>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-translations)
     */
    @GET("tv/{tv_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-credits)
     */
    @GET("tv/{tv_id}/credits")
    @CharJobPerson("cast")
    suspend fun cast(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-aggregate-credits)
     */
    @GET("tv/{tv_id}/aggregate_credits")
    @CharJobPerson("crew")
    suspend fun aggregateCrew(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-aggregate-credits)
     */
    @GET("tv/{tv_id}/aggregate_credits")
    @CharJobPerson("cast")
    suspend fun aggregateCast(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CastRole>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-credits)
     */
    @GET("tv/{tv_id}/credits")
    @CharJobPerson(fieldName = "crew")
    suspend fun crew(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<Pair<TmdbPerson.Slim, TmdbPerson.CrewJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-watch-providers)
     *
     * **Map key**: Country code, for example: DE, US, FR
     */
    @GET("tv/{tv_id}/watch/providers")
    @OtherCases
    suspend fun watchProviders(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<Map<String, TmdbWatchProviderListObject>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-latest-tv)
     */
    @GET("tv/latest")
    suspend fun latest(
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbShow, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-airing-today)
     */
    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-on-the-air)
     */
    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-popular-tv-shows)
     */
    @GET("tv/popular")
    suspend fun popular(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-top-rated-tv)
     */
    @GET("tv/top_rated")
    suspend fun topRated(
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null
    ): NetworkResponse<TmdbPage<TmdbShow.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-episode-groups)
     */
    @GET("tv/{tv_id}/episode_groups")
    @ResultsList
    suspend fun episodeGroups(
        @Path("tv_id") tvShowId: Int
    ): NetworkResponse<List<TmdbEpisodeGroupListObject>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv-episode-groups/get-tv-episode-group-details)
     */
    @GET("tv/episode_group/{id}")
    suspend fun episodeGroupDetails(
        @Path("id") groupId: String,
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbEpisodeGroupListObject, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/tv/get-tv-account-states)
     */
    @GET("tv/{tv_id}/account_states")
    suspend fun accountState(
        @Path("tv_id") tvId: Int
    ) : NetworkResponse<TmdbAccountState.Show, TmdbError>
}