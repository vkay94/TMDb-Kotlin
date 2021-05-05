package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.annotations.CharJob
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-details)
     */
    @GET("person/{person_id}")
    suspend fun details(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null,
        @Query("append_to_response") append: AppendToResponse? = null
    ): NetworkResponse<TmdbPerson, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-translations)
     */
    @GET("person/{person_id}/translations")
    @ResultsList("translations")
    suspend fun translations(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbTranslation>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-images)
     */
    @GET("person/{person_id}/images")
    @ResultsList
    suspend fun profiles(
        @Path("person_id") personId: Int
    ): NetworkResponse<List<TmdbImage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-external-ids)
     */
    @GET("person/{person_id}/external_ids")
    suspend fun externalIds(
        @Path("person_id") personId: Int
    ): NetworkResponse<TmdbExternalIds, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-tv-credits)
     */
    @GET("person/{person_id}/tv_credits")
    @CharJob(fieldName = "cast", mediaType = MediaType.TV)
    suspend fun tvCast(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<TmdbShow.Slim, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-tv-credits)
     */
    @GET("person/{person_id}/tv_credits")
    @CharJob(fieldName = "crew", mediaType = MediaType.TV)
    suspend fun tvCrew(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<TmdbShow.Slim, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-movie-credits)
     */
    @GET("person/{person_id}/movie_credits")
    @CharJob(fieldName = "cast", mediaType = MediaType.MOVIE)
    suspend fun movieCast(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<TmdbMovie.Slim, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-movie-credits)
     */
    @GET("person/{person_id}/movie_credits")
    @CharJob(fieldName = "crew", mediaType = MediaType.MOVIE)
    suspend fun movieCrew(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<TmdbMovie.Slim, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-combined-credits)
     */
    @GET("person/{person_id}/combined_credits")
    @CharJob(fieldName = "cast")
    suspend fun combinedCast(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<MediaTypeItem, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-person-combined-credits)
     */
    @GET("person/{person_id}/combined_credits")
    @CharJob("crew")
    suspend fun combinedCrew(
        @Path("person_id") personId: Int,
        @Query("language") language: String? = null
    ): NetworkResponse<List<Pair<MediaTypeItem, TmdbPerson.RoleJob>>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-popular-people)
     */
    @GET("person/popular")
    suspend fun popular(
        @Query("language") language: String? = null,
    ): NetworkResponse<TmdbPage<TmdbPerson.Slim>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/people/get-latest-person)
     */
    @GET("person/latest")
    suspend fun latest(
        @Query("language") language: String? = null
    ): NetworkResponse<TmdbPerson, TmdbError.DefaultError>
}