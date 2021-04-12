package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.ProductionStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate

class MovieServiceTest : BaseServiceTest() {

    @Test
    fun `Get primary data`() = runBlocking {
        val details = TMDb.movieService.details(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        assertFalse(details.adult)
        assertNotNull(details.background)
        assertEquals(86311, details.belongsToCollection?.collectionId)
        assertEquals(356_000_000, details.budget)
        assertEquals(3, details.genres.count())
        assertEquals("https://www.marvel.com/movies/avengers-endgame", details.homepage)
        assertEquals(299534, details.id)
        assertNotNull(details.imdbId)
        assertEquals("en", details.originalLanguage)
        assertEquals("Avengers: Endgame", details.originalTitle)
        assertTrue(details.overview.contains("After the devastating events of Avengers: Infinity War, "))
        assertEquals(349f, details.popularity)
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
}