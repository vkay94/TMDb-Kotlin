package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SearchServiceTest : BaseServiceTest() {

    @Test
    fun `Search for TV Shows without specifications`() = runBlocking {
        val searchPage = TMDb.searchService.tv(QUERY_MHA).invoke()!!

        assertEquals(1, searchPage.page)
        assertEquals(1, searchPage.totalPages)
        assertFalse(searchPage.results.isEmpty())

        val firstResult = searchPage.results.first()
        assertEquals(MediaType.TV, firstResult.mediaType)
    }

    @Test
    fun `Search for movies without specifications`() = runBlocking {
        val searchPage = TMDb.searchService.movie(QUERY_AVENGERS).invoke()!!
        assertTrue(searchPage.totalPages > 0)
        assertTrue(searchPage.totalResults > 3)

        val firstResult = searchPage.results.first()
        assertEquals(MediaType.MOVIE, firstResult.mediaType)
    }

    @Test
    fun `Search for persons without specifications`() = runBlocking {
        val searchPage = TMDb.searchService.person(QUERY_WILL_SMITH).invoke()!!
        assertTrue(searchPage.totalPages > 1)
        assertFalse(searchPage.results.isEmpty())

        val firstResult = searchPage.results.first()
        assertEquals(MediaType.PERSON, firstResult.mediaType)
        assertEquals("Acting", firstResult.knownForDepartment)
        assertNull(firstResult.originalName)
    }

    @Test
    fun `Search multi`() = runBlocking {
        val searchPage = TMDb.searchService.multi(QUERY_WILL_SMITH).invoke()!!.results
        val tvResults = searchPage.filterIsInstance<TmdbShow.Slim>()
        val movieResults = searchPage.filterIsInstance<TmdbMovie.Slim>()
        val personResults = searchPage.filterIsInstance<TmdbPerson.Slim>()

        assertEquals(2, tvResults.size)
        assertTrue(movieResults.isNotEmpty())
        assertTrue(personResults.isNotEmpty())
    }

    @Test
    fun `Search for keywords`() = runBlocking {
        val searchResult = TMDb.searchService.keyword(QUERY_KEY_ANIME, 1).invoke()!!

        assertEquals(1, searchResult.page)
        assertEquals(1, searchResult.totalPages)
        assertTrue(searchResult.totalResults > 2)
        assertFalse(searchResult.hasNextPage)
    }

    @Test
    fun `Search for companies`() = runBlocking {
        val searchResult = TMDb.searchService.company(QUERY_COMPANY_DISNEY, 1).invoke()!!

        assertEquals(1, searchResult.page)
        assertEquals(20, searchResult.results.size)
        assertTrue(searchResult.totalPages > 2)
        assertTrue(searchResult.totalResults > 40)
        assertTrue(searchResult.hasNextPage)
    }

    @Test
    fun `Search collections`() = runBlocking {
        val searchResult = TMDb.searchService.collection("Kizumono").invoke()!!
        val collection = searchResult.results.first()
        assertEquals(1, searchResult.totalResults)

        assertEquals(374202, collection.collectionId)
        assertEquals("Kizumonogatari", collection.name)
        assertTrue(!collection.overview.isNullOrBlank())
    }
}