package de.vkay.api.tmdb

import com.beust.klaxon.Klaxon
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import de.vkay.api.tmdb.enumerations.*
import de.vkay.api.tmdb.internals.EnumValueJsonAdapter
import de.vkay.api.tmdb.internals.adapters.*
import de.vkay.api.tmdb.internals.annotations.*
import de.vkay.api.tmdb.internals.models.TmdbFindResult
import de.vkay.api.tmdb.internals.models.WatchProviderListObj
import de.vkay.api.tmdb.models.*
import de.vkay.api.tmdb.services.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object TMDb {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_URL_4 = "https://api.themoviedb.org/4/"

    private const val DEFAULT_LANGUAGE = "en-US"

    private lateinit var client: OkHttpClient
    lateinit var tmdbInterceptor: TMDbInterceptor
    private lateinit var apiKey: String
    private lateinit var bearerToken: String

    fun init(apiKey: String, bearerToken: String = "", client: OkHttpClient? = null) = apply {
        this.apiKey = apiKey
        this.bearerToken = bearerToken
        this.client = client ?: defaultOkHttpClient()
    }

    var accessToken: String? = null
        set(value) {
            tmdbInterceptor.setAccessToken(value)
            field = value
        }

    var sessionId: String? = null
        set(value) {
            tmdbInterceptor.setSessionId(value)
            field = value
        }

    fun onlineCondition(condition: () -> Boolean) = apply {
        tmdbInterceptor.onlineCondition(condition)
    }

    fun onlineCache(duration: Long, unit: TimeUnit) = apply {
        tmdbInterceptor.onlineCache(duration, unit)
    }

    fun offlineCache(duration: Long, unit: TimeUnit) = apply {
        tmdbInterceptor.offlineCache(duration, unit)
    }

    val searchService: SearchService by lazy { retrofit3.create(SearchService::class.java) }
    val showService: TvService by lazy { retrofit3.create(TvService::class.java) }
    val seasonService: TvSeasonService by lazy { retrofit3.create(TvSeasonService::class.java) }
    val episodeService: TvEpisodeService by lazy { retrofit3.create(TvEpisodeService::class.java) }
    val movieService: MovieService by lazy { retrofit3.create(MovieService::class.java) }
    val personService: PersonService by lazy { retrofit3.create(PersonService::class.java) }
    val genreService: GenreService by lazy { retrofit3.create(GenreService::class.java) }
    val keywordService: KeywordService by lazy { retrofit3.create(KeywordService::class.java) }
    val networkService: NetworkService by lazy { retrofit3.create(NetworkService::class.java) }
    val companyService: CompanyService by lazy { retrofit3.create(CompanyService::class.java) }
    val findService: FindService by lazy { retrofit3.create(FindService::class.java) }
    val discoverService: DiscoverService by lazy { retrofit3.create(DiscoverService::class.java) }
    val trendingService: TrendingService by lazy { retrofit3.create(TrendingService::class.java) }
    val collectionService: CollectionService by lazy { retrofit3.create(CollectionService::class.java) }

    val listService: ListService by lazy { retrofit4.create(ListService::class.java) }
    val authService: AuthService by lazy { retrofit4.create(AuthService::class.java) }
    val accountService: AccountService by lazy { retrofit3.create(AccountService::class.java) }

    private val moshiWithAdapters: Moshi by lazy {
        return@lazy Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(MediaTypeItem::class.java, "media_type")
                    .withSubtype(TmdbShow.Slim::class.java, "tv")
                    .withSubtype(TmdbMovie.Slim::class.java, "movie")
                    .withSubtype(TmdbPerson.Slim::class.java, "person")
            )

            /* Enums with fallbacks */
            .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
                .withUnknownFallback(PersonGender.OTHER))
            .add(EpisodeGroupType::class.java, EnumValueJsonAdapter.create(EpisodeGroupType::class.java)
                .withUnknownFallback(EpisodeGroupType.UNDEFINED))
            .add(
                TmdbVideo.Site::class.java, EnumJsonAdapter.create(TmdbVideo.Site::class.java)
                    .withUnknownFallback(TmdbVideo.Site.UNDEFINED))
            .add(
                TmdbVideo.Type::class.java, EnumJsonAdapter.create(TmdbVideo.Type::class.java)
                    .withUnknownFallback(TmdbVideo.Type.UNDEFINED))
            .add(
                ShowType::class.java, EnumJsonAdapter.create(ShowType::class.java)
                    .withUnknownFallback(ShowType.UNKNOWN))
            .add(
                ListSortBy::class.java, EnumJsonAdapter.create(ListSortBy::class.java)
                    .withUnknownFallback(ListSortBy.ORIGINAL_ORDER_ASC))
            .add(
                ReleaseType::class.java, EnumValueJsonAdapter.create(ReleaseType::class.java)
                    .withUnknownFallback(ReleaseType.UNKNOWN))

            .add(
                MediaType::class.java, EnumJsonAdapter.create(MediaType::class.java)
                    .withUnknownFallback(MediaType.UNKNOWN))

            /* Helpers: Types / Maps */

            /* Custom writer adapters */
            .add(TmdbTranslation.Data::class.java, TmdbTranslationDataJsonAdapter())
            .add(TmdbPerson.Cast::class.java, TmdbCreditCastJsonAdapter())
            .add(TmdbPerson.Crew::class.java, TmdbCreditCrewJsonAdapter())
            .add(TmdbPerson.RoleJob::class.java, TmdbCreditRoleJobJsonAdapter())
            .add(TmdbFindResult.ADAPTER)
            .add(TmdbDate.ADAPTER)

            /* Objects to lists such as watch providers */
            .add(WatchProviderListObj.ADAPTER)

            /* Message + error handling: for sealed class and those with annotations */
            .add(TmdbError::class.java, TmdbErrorJsonAdapter())
            .add(ErrorAnnotationAdapter())
            .add(ResultsListAdapter.INSTANCE)
            .add(MapListAdapter.INSTANCE)
            .add(TMDbImageAdapter.INSTANCE)
            .add(RatedJsonAdapter())
            .build()
    }

    private val retrofit3: Retrofit by lazy {
        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshiWithAdapters))
            .client(client)
            .build()
    }

    private val retrofit4: Retrofit by lazy {
        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL_4)
            .addConverterFactory(MoshiConverterFactory.create(moshiWithAdapters))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .build()
    }

    private fun defaultOkHttpClient(): OkHttpClient {
        tmdbInterceptor = TMDbInterceptor(apiKey, bearerToken)
        return OkHttpClient.Builder().addInterceptor(tmdbInterceptor).build()
    }

    private val networks: Map<String, Int> by lazy {
        val hashMap = HashMap<String, Int>()
        val file = Thread.currentThread().contextClassLoader.getResourceAsStream("tv_network_ids.json")

        file?.let { f ->
            f.bufferedReader().readLines().forEach {
                Klaxon().parse<NetworkPair>(it)?.let { pair -> hashMap[pair.name] = pair.id }
            }
        }

        return@lazy hashMap
    }

    private data class NetworkPair(val id: Int, val name: String)

    fun searchNetworks(query: String): Map<String, Int> {
        return networks.filter { it.key.contains(query, true) }
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

    fun authLink(requestToken: String) =
        "https://www.themoviedb.org/auth/access?request_token=$requestToken"
}