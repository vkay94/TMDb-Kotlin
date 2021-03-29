package de.vkay.api.tmdb

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import de.vkay.api.tmdb.enumerations.EpisodeGroupType
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.enumerations.ShowType
import de.vkay.api.tmdb.internals.*
import de.vkay.api.tmdb.internals.adapters.*
import de.vkay.api.tmdb.internals.adapters.listmap.*
import de.vkay.api.tmdb.models.*
import de.vkay.api.tmdb.services.*
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


object TMDb {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_URL_4 = "https://api.themoviedb.org/4/"

    private const val DEFAULT_LANGUAGE = "en-US"

    private lateinit var client: OkHttpClient
    private lateinit var apiKey: String
    private var accessToken: String? = null

    init {
        // println("TMDb: Init block")
    }

    fun init(apiKey: String, client: OkHttpClient? = null) = apply {
        this.apiKey = apiKey
        this.client = client ?: defaultOkHttpClient(apiKey)
    }

    val configurationService: ConfigurationService by lazy { retrofit3.create(ConfigurationService::class.java) }
    val searchService: SearchService by lazy { retrofit3.create(SearchService::class.java) }
    val showService: TvService by lazy { retrofit3.create(TvService::class.java) }
    val seasonService: TvSeasonService by lazy { retrofit3.create(TvSeasonService::class.java) }
    val episodeService: TvEpisodeService by lazy { retrofit3.create(TvEpisodeService::class.java) }
    val personService: PersonService by lazy { retrofit3.create(PersonService::class.java) }
    val genreService: GenreService by lazy { retrofit3.create(GenreService::class.java) }
    val keywordService: KeywordService by lazy { retrofit3.create(KeywordService::class.java) }

    private val mapWatchType = Types.newParameterizedType(Map::class.java, String::class.java, TmdbWatchProviderList::class.java)
    private val mapAdapter: JsonAdapter<Map<String, TmdbWatchProviderList>> =
        Moshi.Builder().build().adapter(mapWatchType)

    private val moshiWithAdapters: Moshi by lazy {
        // println("TMDb: Create Moshi Builder")
        return@lazy Moshi.Builder()
            .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
                .withUnknownFallback(PersonGender.OTHER))
            .add(EpisodeGroupType::class.java, EnumValueJsonAdapter.create(EpisodeGroupType::class.java)
                .withUnknownFallback(EpisodeGroupType.UNDEFINED))
            .add(TmdbDateJsonAdapter())
            .add(
                PolymorphicJsonAdapterFactory.of(MediaTypeItem::class.java, "media_type")
                    .withSubtype(TmdbShowListObject::class.java, "tv")
                    .withSubtype(TmdbMovieListObject::class.java, "movie")
                    .withSubtype(TmdbPersonListObject::class.java, "person")
            )

            /* Enums with fallbacks */
            .add(
                TmdbVideo.Site::class.java, EnumJsonAdapter.create(TmdbVideo.Site::class.java)
                    .withUnknownFallback(TmdbVideo.Site.UNDEFINED)
            )
            .add(
                TmdbVideo.Type::class.java, EnumJsonAdapter.create(TmdbVideo.Type::class.java)
                    .withUnknownFallback(TmdbVideo.Type.UNDEFINED)
            )
            .add(
                ShowType::class.java, EnumJsonAdapter.create(ShowType::class.java)
                    .withUnknownFallback(ShowType.UNKNOWN)
            )

            /* Helpers: Types / Maps */
            .add(mapWatchType, mapAdapter)

            /* Custom writer adapters */
            .add(TmdbTranslationData::class.java, TmdbTranslationDataJsonAdapter())
            .add(TmdbCredit::class.java, TmdbCreditJsonAdapter())

            /* Objects to lists such as videos, genres and keywords which have a single field */
            .add(TmdbVideosListJsonAdapter())
            .add(TmdbGenresListJsonAdapter())
            .add(TmdbKeywordsListJsonAdapter())
            .add(TmdbContentRatingsListJsonAdapter())
            .add(TmdbEpisodeGroupsListJsonAdapter())
            .add(TmdbAlternativeTitlesListJsonAdapter())
            .add(TmdbTranslationsListJsonAdapter())
            .add(TmdbWatchProviderMapListJsonAdapter())
            .add(TmdbErrorJsonAdapter())
            .build()
    }

    private val retrofit3: Retrofit by lazy {
        // println("TMDb: Create Retrofit")
        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiWithAdapters))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .build()
    }

    private fun defaultOkHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(TMDbInterceptor(apiKey)).build()
    }

    // Links

    fun tvLink(tmdbId: Int, langTag: String = DEFAULT_LANGUAGE) =
        "https://www.themoviedb.org/tv/$tmdbId?language=$langTag"

    fun seasonLink(tmdbId: Int, season: Int, langTag: String = DEFAULT_LANGUAGE) =
        "https://www.themoviedb.org/tv/$tmdbId/season/$season?language=$langTag"

    fun episodeLink(tmdbId: Int, season: Int, episode: Int, langTag: String = DEFAULT_LANGUAGE) =
        "https://www.themoviedb.org/tv/$tmdbId/season/$season/episode/$episode?language=$langTag"

    fun movieLink(tmdbId: Int, langTag: String = DEFAULT_LANGUAGE) =
        "https://www.themoviedb.org/movie/$tmdbId?language=$langTag"

    fun personLink(tmdbId: Int, langTag: String = DEFAULT_LANGUAGE) =
        "https://www.themoviedb.org/person/$tmdbId?language=$langTag"
}