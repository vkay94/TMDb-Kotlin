package services

import API_KEY
import com.haroldadmin.cnradapter.invoke
import de.vkay.tmdb.TMDb
import de.vkay.tmdb.enumerations.SearchMedia
import de.vkay.tmdb.models.TmdbMovieListObject
import de.vkay.tmdb.models.TmdbPersonListObject
import de.vkay.tmdb.models.TmdbShowListObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class SearchServiceTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY) }
    }

    @Before
    fun setup() {  }

    @Test
    fun `Search for TV Shows without specifications`() = runBlocking {
        val searchResult = TMDb.searchService.tv(QUERY_MHA).invoke()!!
        assertEquals(1, searchResult.totalPages)
        assertFalse(searchResult.results.isEmpty())

        val firstResult = searchResult.results.first()
        assertEquals(SearchMedia.TV, firstResult.searchType)
    }

    @Test
    fun `Search for movies without specifications`() = runBlocking {
        val searchResult = TMDb.searchService.movie(QUERY_AVENGERS).invoke()!!
        assertTrue(searchResult.totalPages > 0)
        assertTrue(searchResult.totalResults > 3)

        val firstResult = searchResult.results.first()
        assertEquals(SearchMedia.MOVIE, firstResult.searchType)
    }

    @Test
    fun `Search for persons without specifications`() = runBlocking {
        val searchResult = TMDb.searchService.person(QUERY_WILL_SMITH).invoke()!!
        assertTrue(searchResult.totalPages > 1)
        assertFalse(searchResult.results.isEmpty())

        val firstResult = searchResult.results.first()
        assertEquals(SearchMedia.PERSON, firstResult.searchType)
    }

    @Test
    fun `Search multi`() = runBlocking {
        val searchResult = TMDb.searchService.multi(QUERY_WILL_SMITH).invoke()!!.results
        val tvResults = searchResult.filterIsInstance<TmdbShowListObject>()
        val movieResults = searchResult.filterIsInstance<TmdbMovieListObject>()
        val personResults = searchResult.filterIsInstance<TmdbPersonListObject>()

        assertEquals(2, tvResults.size)
        assertTrue(movieResults.isNotEmpty())
        assertTrue(personResults.isNotEmpty())
    }

    @Test
    fun `Search for keywords`() = runBlocking {
        val searchResult = TMDb.searchService.keyword(QUERY_KEY_ANIME).invoke()!!
        assertTrue(searchResult.totalResults > 0)
    }
}