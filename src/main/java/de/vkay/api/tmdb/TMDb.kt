package de.vkay.api.tmdb

import com.beust.klaxon.Klaxon
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
import de.vkay.api.tmdb.internals.auth.TmdbRequestTokenResponseJsonAdapterHelper
import de.vkay.api.tmdb.models.*
import de.vkay.api.tmdb.services.*
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import kotlin.collections.HashMap


object TMDb {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_URL_4 = "https://api.themoviedb.org/4/"

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

    val configurationService: ConfigurationService by lazy { retrofit3.create(ConfigurationService::class.java) }
    val searchService: SearchService by lazy { retrofit3.create(SearchService::class.java) }
    val showService: TvService by lazy { retrofit3.create(TvService::class.java) }
    val seasonService: TvSeasonService by lazy { retrofit3.create(TvSeasonService::class.java) }
    val episodeService: TvEpisodeService by lazy { retrofit3.create(TvEpisodeService::class.java) }
    val personService: PersonService by lazy { retrofit3.create(PersonService::class.java) }
    val genreService: GenreService by lazy { retrofit3.create(GenreService::class.java) }
    val keywordService: KeywordService by lazy { retrofit3.create(KeywordService::class.java) }
    val networkService: NetworkService by lazy { retrofit3.create(NetworkService::class.java) }
    val companyService: CompanyService by lazy { retrofit3.create(CompanyService::class.java) }
    val findService: FindService by lazy { retrofit3.create(FindService::class.java) }
    val discoverService: DiscoverService by lazy { retrofit3.create(DiscoverService::class.java) }
    val trendingService: TrendingService by lazy { retrofit3.create(TrendingService::class.java) }

    val listService: ListService by lazy { retrofit4.create(ListService::class.java) }
    val authService: AuthService by lazy { retrofit4.create(AuthService::class.java) }

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
            .add(TmdbFindJsonAdapter())
            .add(TmdbDateJsonAdapter())

            /* Objects to lists such as videos, genres and keywords which have a single field */
            .add(TmdbVideosListJsonAdapter())
            .add(TmdbGenresListJsonAdapter())
            .add(TmdbKeywordsListJsonAdapter())
            .add(TmdbContentRatingsListJsonAdapter())
            .add(TmdbEpisodeGroupsListJsonAdapter())
            .add(TmdbAlternativeTitlesListJsonAdapter())
            .add(TmdbTranslationsListJsonAdapter())
            .add(TmdbWatchProviderMapListJsonAdapter())
            .add(TmdbNetworkImagesListJsonAdapter())
            .add(TmdbRequestTokenResponseJsonAdapterHelper())

            /* Error handling: for sealed class and those with annotations */
            .add(TmdbError::class.java, TmdbErrorJsonAdapter())
            .add(TmdbErrorListMapJsonAdapter())
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

    private val retrofit4: Retrofit by lazy {
        // println("TMDb: Create Retrofit")
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