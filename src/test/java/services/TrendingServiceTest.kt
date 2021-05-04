package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.enumerations.Trending
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TrendingServiceTest : BaseServiceTest() {

    @Test
    fun `Get trending movies`() = runBlocking {
        val trending = TMDb.trendingService.get(Trending.Type.MOVIE, Trending.TimeWindow.DAY).invoke()!!
        assertTrue(trending.totalResults > 100)
        assertTrue(trending.totalPages > 100)

        val firstMovie = trending.results.first()
        assertEquals(MediaType.MOVIE, firstMovie.mediaType)
    }

    @Test
    fun `Get trending shows`() = runBlocking {
        val trending = TMDb.trendingService.get(Trending.Type.TV, Trending.TimeWindow.DAY).invoke()!!
        assertTrue(trending.totalResults > 100)
        assertTrue(trending.totalPages > 100)

        val firstShow = trending.results.first()
        assertEquals(MediaType.TV, firstShow.mediaType)
    }

    @Test
    fun `Get trending persons`() = runBlocking {
        val trending = TMDb.trendingService.get(Trending.Type.PERSON, Trending.TimeWindow.DAY).invoke()!!
        assertTrue(trending.totalResults > 100)
        assertTrue(trending.totalPages > 100)

        val firstPerson = trending.results.first()
        assertEquals(MediaType.PERSON, firstPerson.mediaType)
    }
}