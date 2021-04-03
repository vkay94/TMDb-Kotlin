package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbMovieListObject
import de.vkay.api.tmdb.models.TmdbPersonListObject
import de.vkay.api.tmdb.models.TmdbShowListObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

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
    }

    @Test
    fun `Search multi`() = runBlocking {
        val searchPage = TMDb.searchService.multi(QUERY_WILL_SMITH).invoke()!!.results
        val tvResults = searchPage.filterIsInstance<TmdbShowListObject>()
        val movieResults = searchPage.filterIsInstance<TmdbMovieListObject>()
        val personResults = searchPage.filterIsInstance<TmdbPersonListObject>()

        assertEquals(2, tvResults.size)
        assertTrue(movieResults.isNotEmpty())
        assertTrue(personResults.isNotEmpty())
    }

    @Test
    fun `Search for keywords`() = runBlocking {
        val searchResult = TMDb.searchService.keyword(QUERY_KEY_ANIME, 1).invoke()!!

        assertEquals(1, searchResult.page)
        assertEquals(1, searchResult.totalPages)
        assertEquals(4, searchResult.totalResults)
        assertFalse(searchResult.hasNextPage)
    }

    @Test
    fun `Search for companies`() = runBlocking {
        val searchResult = TMDb.searchService.company(QUERY_COMPANY_DISNEY, 1).invoke()!!

        assertEquals(1, searchResult.page)
        assertEquals(3, searchResult.totalPages)
        assertEquals(41, searchResult.totalResults)
        assertEquals(20, searchResult.results.size)
        assertTrue(searchResult.hasNextPage)
    }

    @Test
    fun `Search for networks (locally)`() = runBlocking {
        val searchResult = TMDb.searchNetworks("Netflix")
        val searchResult2 = TMDb.searchNetworks("crun")
        println(searchResult)
        println(searchResult2)
    }
}