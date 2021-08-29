package de.vkay.api.tmdb.services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.internals.annotations.ResultsList
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbLanguage
import de.vkay.api.tmdb.models.TmdbRegion
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationService {

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/configuration/get-countries)
     */
    @GET("configuration/countries")
    @ResultsList
    suspend fun countries(
        @Query("language") languageTag: String? = null
    ): NetworkResponse<List<TmdbRegion>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/configuration/get-languages)
     */
    @GET("configuration/languages")
    @ResultsList
    suspend fun languages(): NetworkResponse<List<TmdbLanguage>, TmdbError.DefaultError>

    /**
     * Reference: [The Movie Database API](https://developers.themoviedb.org/3/configuration/get-primary-translations)
     */
    @GET("configuration/primary_translations")
    @ResultsList
    suspend fun primaryTranslations(): NetworkResponse<List<String>, TmdbError.DefaultError>
}