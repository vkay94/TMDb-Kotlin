package services

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.ProductionStatus
import de.vkay.api.tmdb.enumerations.ReleaseType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*

class MovieServiceTest : BaseServiceTest() {

    @Test
    fun `Get primary data`() = runBlocking {
        val details = TMDb.movieService.details(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        assertFalse(details.adult)
        assertNotNull(details.backdrop)
        assertEquals(86311, details.belongsToCollection?.collectionId)
        assertEquals(356_000_000, details.budget)
        assertEquals(3, details.genres.count())
        assertEquals("https://www.marvel.com/movies/avengers-endgame", details.homepage)
        assertEquals(299534, details.id)
        assertNotNull(details.imdbId)
        assertEquals("en", details.originalLanguage)
        assertEquals("Avengers: Endgame", details.originalTitle)
        assertTrue(details.overview.contains("After the devastating events of Avengers: Infinity War, "))
        assertNotNull(details.poster)
        assertEquals("US", details.productionCountries.first().countryCode)
        assertEquals(420, details.productionCompanies.first().id)
        assertEquals(LocalDate.of(2019, 4, 24), details.releaseDate?.date)
        assertTrue(details.revenue > 2_797_800_000)
        assertEquals(181, details.runtime)
        assertTrue(details.spokenLanguages.any { it.languageCode == "en" })
        assertEquals(ProductionStatus.RELEASED, details.status)
        assertEquals("Part of the journey is the end.", details.tagline)
        assertEquals("Avengers: Endgame", details.title)
        assertFalse(details.video)
        assertTrue(details.voteAverage > 5)
        assertTrue(details.voteCount > 17_000)
    }

    @Test
    fun `Get external ids`() = runBlocking {
        val externalIds = TMDb.movieService.externalIds(MOVIE_ID_AVENGERS_ENDGAME).invoke()
        assertNotNull({externalIds?.imdb})
        assertNotNull({externalIds?.tvdb})
    }

    @Test
    fun `Get added fields data`() = runBlocking {
        val details = TMDb.movieService.details(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        assertTrue(details.videos.isEmpty())
        assertTrue(details.posters.isEmpty())
        assertTrue(details.backdrops.isEmpty())
        assertTrue(details.recommendations.isEmpty())
    }

    @Test
    fun `Get appended info`(): Unit = runBlocking {
        val append = AppendToResponse(
            AppendToResponse.Item.IMAGES, AppendToResponse.Item.VIDEOS, AppendToResponse.Item.KEYWORDS,
            AppendToResponse.Item.SIMILAR, AppendToResponse.Item.RECOMMENDATIONS,
            AppendToResponse.Item.EXTERNAL_IDS
        )

        val details = TMDb.movieService.details(MOVIE_ID_AVENGERS_ENDGAME, append = append).invoke()!!
        assertTrue(details.posters.isNotEmpty())
        assertTrue(details.backdrops.isNotEmpty())
        assertTrue(details.logos.isNotEmpty())
        assertTrue(details.videos.isNotEmpty())
        assertTrue(details.keywords.isNotEmpty())
        assertTrue(details.recommendations.isNotEmpty())
        assertTrue(details.similar.isNotEmpty())

        assertEquals(details.imdbId, details.externalIds?.imdb)
    }

    @Test
    fun `Get posters`(): Unit = runBlocking {
        val posters = TMDb.movieService.posters(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(posters.isNotEmpty())
    }

    @Test
    fun `Get backdrops`(): Unit = runBlocking {
        val backdrops = TMDb.movieService.backdrops(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(backdrops.isNotEmpty())
    }

    @Test
    fun `Get logos`(): Unit = runBlocking {
        val logos = TMDb.movieService.backdrops(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(logos.isNotEmpty())
    }

    @Test
    fun `Get recommendations`(): Unit = runBlocking {
        val recommendations = TMDb.movieService.recommendations(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(recommendations.results.isNotEmpty())

        assertEquals(MediaType.MOVIE, recommendations.results.first().mediaType)
    }

    @Test
    fun `Get similar`(): Unit = runBlocking {
        val similar = TMDb.movieService.similar(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(similar.results.isNotEmpty())
    }

    @Test
    fun `Get videos`(): Unit = runBlocking {
        val videos = TMDb.movieService.videos(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(videos.isNotEmpty())

        val videosGerman = TMDb.movieService.videos(MOVIE_ID_AVENGERS_ENDGAME, "de-DE").invoke()!!
        assertTrue(videosGerman.isNotEmpty())
    }

    @Test
    fun `Get keywords`(): Unit = runBlocking {
        val keywords = TMDb.movieService.keywords(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(keywords.isNotEmpty())
    }

    @Test
    fun `Get alternative titles`(): Unit = runBlocking {
        val alternativeTitles = TMDb.movieService.alternativeTitles(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(alternativeTitles.isNotEmpty())
    }

    @Test
    fun `Get translations`(): Unit = runBlocking {
        val translations = TMDb.movieService.translations(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(translations.isNotEmpty())
    }

    @Test
    fun `Get cast`(): Unit = runBlocking {
        val cast = TMDb.movieService.cast(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(cast.isNotEmpty())

        val (person, role) = cast.findLast { it.first.name.contains("Downey") }!!
        assertEquals("Robert Downey Jr.", person.name)
        assertEquals("Tony Stark / Iron Man", role.character)
        assertNull(role.episodeCount)
    }

    @Test
    fun `Get crew`(): Unit = runBlocking {
        val crew = TMDb.movieService.crew(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(crew.isNotEmpty())

        val (person, job) = crew.findLast { it.first.name.contains("Downey") }!!
        assertEquals("Lisa Theresa Downey-Dent", person.name)
        assertEquals("Visual Effects Coordinator", job.job)
        assertNull(job.episodeCount)
    }

    @Test
    fun `Get JustWatch providers`(): Unit = runBlocking {
        val countryMap = TMDb.movieService.watchProviders(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertTrue(countryMap.containsKey("DE"))

        val german = countryMap.getValue("DE")
        assertEquals("https://www.themoviedb.org/movie/299534-avengers-endgame/watch?locale=DE", german.link)
        assertFalse(german.flatrate!!.isEmpty())
        assertFalse(german.buy!!.isEmpty())
        assertNotNull(german.rent)

        assertEquals("Disney Plus", german.flatrate!!.first().name)
        assertEquals(337, german.flatrate!!.first().id)
        assertNotNull(german.flatrate!!.first().logo)
    }

    @Test
    fun `Get release dates`(): Unit = runBlocking {
        val releaseDatesMap = TMDb.movieService.releaseDates(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        val germany = releaseDatesMap[Locale.GERMANY.country]!!
        val theater = germany.first { it.releaseType == ReleaseType.THEATRICAL }
        assertEquals(LocalDate.of(2019, 4, 24), theater.releaseDate.date)
    }

    @Test
    fun `Get popular`(): Unit = runBlocking {
        val popular = TMDb.movieService.popular(languageCode = "de").invoke()!!
        assertTrue(popular.results.isNotEmpty())
    }

    @Test
    fun `Get top rated`(): Unit = runBlocking {
        val topRated = TMDb.movieService.topRated(languageCode = "de").invoke()!!
        assertTrue(topRated.results.isNotEmpty())
    }

    @Test
    fun `Get upcoming`(): Unit = runBlocking {
        val upcoming = TMDb.movieService.upcoming(languageCode = "de").invoke()!!
        assertTrue(upcoming.results.isNotEmpty())
    }

    @Test
    fun `Get now playing`(): Unit = runBlocking {
        val nowPlaying = TMDb.movieService.nowPlaying(languageCode = "de").invoke()!!
        assertTrue(nowPlaying.results.isNotEmpty())
    }

    @Test
    fun `Get latest`(): Unit = runBlocking {
        // TODO
    }

    @Test
    fun `Fail unavailable id`() = runBlocking {
        val invResponse = TMDb.movieService.details(987654321)
        assertTrue(invResponse is NetworkResponse.ServerError)

        with(invResponse as NetworkResponse.ServerError) {
            assertEquals(404, code)
            assertEquals(34, body?.code)
            assertEquals("The resource you requested could not be found.", body?.message)
        }
    }
}