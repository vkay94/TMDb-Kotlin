package services

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.IncludeLanguages
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.EpisodeGroupType
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.ProductionStatus
import de.vkay.api.tmdb.models.TmdbCountry
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TvServiceTest : BaseServiceTest() {

    @Test
    fun `Get external ids`() = runBlocking {
        val externalIds = TMDb.showService.externalIds(SHOW_ID_MHA).invoke()
        assertNotNull({externalIds?.imdb})
        assertNotNull({externalIds?.tvdb})
    }

    @Test
    fun `Get primary data`() = runBlocking {
        val details = TMDb.showService.details(SHOW_ID_MHA).invoke()!!

        assertEquals("My Hero Academia", details.title)
        assertEquals(SHOW_ID_MHA, details.id)
        assertEquals(LocalDate.of(2016, 4, 3), details.firstAirDate?.date)
        assertEquals("ja", details.originalLanguage)
        assertEquals(listOf("JP"), details.originalCountries)
        assertTrue(details.overview.isNotBlank())
        assertEquals(MediaType.TV, details.mediaType)
        assertTrue(details.productionCompanies.any { it.id == 2849 })
        assertTrue(details.productionCountries.contains(TmdbCountry("JP", "Japan")))

        assertEquals(ProductionStatus.RETURNING, details.currentStatus)
    }

    @Test
    fun `Get added fields data`() = runBlocking {
        val details = TMDb.showService.details(SHOW_ID_MHA).invoke()!!

        assertEquals(24, details.runtime)
        assertNotNull(details.poster)
        assertNotNull(details.backdrop)
        assertTrue(details.hasSpecials)
        assertTrue(details.videos.isEmpty())
        assertTrue(details.posters.isEmpty())
        assertTrue(details.backdrops.isEmpty())
        assertTrue(details.recommendations.isEmpty())
    }

    @Test
    fun `Get appended info`(): Unit = runBlocking {
        val append = AppendToResponse(
            AppendToResponse.Item.IMAGES, AppendToResponse.Item.VIDEOS, AppendToResponse.Item.KEYWORDS
        )

        val details = TMDb.showService.details(SHOW_ID_MHA, append = append).invoke()!!
        assertTrue(details.posters.isNotEmpty())
        assertTrue(details.backdrops.isNotEmpty())
        assertTrue(details.logos.isNotEmpty())
        assertTrue(details.videos.isNotEmpty())
        assertTrue(details.keywords.isNotEmpty())

        assertTrue(details.recommendations.isEmpty())
        assertTrue(details.similar.isEmpty())
    }

    @Test
    fun `Get posters`(): Unit = runBlocking {
        val posters = TMDb.showService.posters(SHOW_ID_MHA).invoke()!!
        assertTrue(posters.isNotEmpty())
    }

    @Test
    fun `Get backdrops`(): Unit = runBlocking {
        val backdrops = TMDb.showService.backdrops(SHOW_ID_MHA).invoke()!!
        assertTrue(backdrops.isNotEmpty())
    }

    @Test
    fun `Get logos`(): Unit = runBlocking {
        val logos = TMDb.showService.logos(SHOW_ID_MHA).invoke()!!
        assertTrue(logos.isNotEmpty())
    }

    @Test
    fun `Get recommendations`(): Unit = runBlocking {
        val recommendations = TMDb.showService.recommendations(SHOW_ID_MHA).invoke()!!
        assertTrue(recommendations.results.isNotEmpty())

        assertEquals(MediaType.TV, recommendations.results.first().mediaType)
    }

    @Test
    fun `Get similar`(): Unit = runBlocking {
        val similar = TMDb.showService.similar(SHOW_ID_MHA).invoke()!!
        assertTrue(similar.results.isNotEmpty())
    }

    @Test
    fun `Get videos`(): Unit = runBlocking {
        val videos = TMDb.showService.videos(SHOW_ID_MHA).invoke()!!
        assertTrue(videos.isNotEmpty())
        assertFalse(videos.any { it.languageCode == "de" })

        val includeVideoLanguages = TMDb.showService.videos(SHOW_ID_MHA,
            includeLanguages = IncludeLanguages("de")).invoke()!!
        assertTrue(includeVideoLanguages.any { it.languageCode == "de" })
    }

    @Test
    fun `Get keywords`(): Unit = runBlocking {
        val keywords = TMDb.showService.keywords(SHOW_ID_MHA).invoke()!!
        assertTrue(keywords.isNotEmpty())
    }

    @Test
    fun `Get content ratings`(): Unit = runBlocking {
        val contentRatings = TMDb.showService.contentRatings(SHOW_ID_MHA).invoke()!!
        assertTrue(contentRatings.isNotEmpty())

        val ratingUS = contentRatings.first { it.countryCode == "US" }
        assertEquals("TV-14", ratingUS.rating)
    }

    @Test
    fun `Get alternative titles`(): Unit = runBlocking {
        val alternativeTitles = TMDb.showService.alternativeTitles(SHOW_ID_MHA).invoke()!!
        assertTrue(alternativeTitles.isNotEmpty())
    }

    @Test
    fun `Get translations`(): Unit = runBlocking {
        val translations = TMDb.showService.translations(SHOW_ID_BLACK_CLOVER).invoke()!!
        assertTrue(translations.isNotEmpty())
    }

    @Test
    fun `Get cast`(): Unit = runBlocking {
        val cast = TMDb.showService.cast(SHOW_ID_TBBT).invoke()!!
        assertEquals(8, cast.count())

        val (person, role) = cast.find { it.first.name.contains("Kaley") }!!
        assertEquals("Kaley Cuoco", person.name)
        assertEquals("Penny", role.character)
        assertNull(role.episodeCount)
    }

    @Test
    fun `Get aggregate cast`(): Unit = runBlocking {
        val cast = TMDb.showService.aggregateCast(SHOW_ID_TBBT).invoke()!!
        assertTrue(cast.isNotEmpty())

        val (person, role) = cast.find { it.first.name.contains("Kaley") }!!
        assertEquals("Kaley Cuoco", person.name)
        assertEquals("Penny", role.character)
        assertEquals(279, role.episodeCount)
    }

    @Test
    fun `Get crew`(): Unit = runBlocking {
        val crew = TMDb.showService.crew(SHOW_ID_TBBT).invoke()!!
        assertTrue(crew.isNotEmpty())

        val (person, job) = crew.find { it.first.name == "Francoise Cherry-Cohen" }!!
        assertEquals("Francoise Cherry-Cohen", person.name)
        assertEquals("Art Direction", job.job)
        assertNull(job.episodeCount)
    }

    @Test
    fun `Get JustWatch providers`(): Unit = runBlocking {
        val countryMap = TMDb.showService.watchProviders(SHOW_ID_BLACK_CLOVER).invoke()!!
        assertTrue(countryMap.containsKey("DE"))

        val german = countryMap.getValue("DE")
        assertEquals("https://www.themoviedb.org/tv/73223/watch?locale=DE", german.link)
        assertFalse(german.rent!!.isEmpty())
        assertFalse(german.buy!!.isEmpty())
        assertFalse(german.flatrate!!.isEmpty())

        assertEquals("Anime On Demand", german.rent!!.first().name)
        assertEquals(327, german.rent!!.first().id)
        assertNotNull(german.rent!!.first().logo)
    }

    @Test
    fun `Get latest`(): Unit = runBlocking {
        val latest = TMDb.showService.latest().invoke()!!
        assertTrue(latest is TmdbShow)
    }

    @Test
    fun `Get airing today`(): Unit = runBlocking {
        val airing = TMDb.showService.airingToday().invoke()!!
        assertTrue(airing.results.isNotEmpty())
    }

    @Test
    fun `Get on the air`(): Unit = runBlocking {
        val onTheAir = TMDb.showService.onTheAir().invoke()!!
        assertTrue(onTheAir.results.isNotEmpty())
    }

    @Test
    fun `Get popular`(): Unit = runBlocking {
        val popular = TMDb.showService.popular().invoke()!!
        assertTrue(popular.results.isNotEmpty())
    }

    @Test
    fun `Get top rated`(): Unit = runBlocking {
        val top = TMDb.showService.topRated().invoke()!!
        assertTrue(top.results.isNotEmpty())
    }

    @Test
    fun `Get episode groups`(): Unit = runBlocking {
        val episodeGroups = TMDb.showService.episodeGroups(SHOW_ID_BLACK_CLOVER).invoke()!!
        assertTrue(episodeGroups.isNotEmpty())
    }

    @Test
    fun `Get episode group details`(): Unit = runBlocking {
        val details = TMDb.showService.episodeGroupDetails(EP_GROUP_ID_BLACK_CLOVER_TV).invoke()!!

        assertEquals(EpisodeGroupType.TV, details.type)
        assertEquals("Black clover", details.name)
        assertEquals(3,  details.groupCount)

        val season = details.groups!!.first()
        assertEquals("Season 1", season.name)
        assertEquals(1, season.order)
    }

    @Test
    fun `Fail unavailable id`() = runBlocking {
        val invResponse = TMDb.showService.details(987654321)
        assertTrue(invResponse is NetworkResponse.ServerError)

        with(invResponse as NetworkResponse.ServerError) {
            assertEquals(404, code)
            assertEquals(34, body?.code)
            assertEquals("The resource you requested could not be found.", body?.message)
        }
    }

    @Test
    fun `Simply testing`() = runBlocking {
        val details = TMDb.showService.details(SHOW_ID_BLACK_CLOVER).invoke()!!

//        details.spokenLanguages.forEach {
//            println("Spoken language: $it")
//        }
//
//        println("tagline = ${details.tagline}")
//        println("type = ${details.type}")
    }
}