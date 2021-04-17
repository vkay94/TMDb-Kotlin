package services

import ACCESS_TOKEN
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test


class ListServiceTest : BaseServiceTest() {

    @Test
    fun `Get list details (public)`() = runBlocking {
        val details = TMDb.listService.details(LIST_ID_ANIME).invoke()!!

        assertEquals("Animes", details.name)
        assertEquals(0, details.revenue)
        assertNotNull(details.backdrop)
        assertNull(details.poster)
        assertEquals(ListSortBy.ORIGINAL_ORDER_ASC, details.sortBy)
    }

    @Test
    fun `Get list with sort_by (public)`() = runBlocking {
        // Release dates is not supported for TV shows
        val votes = TMDb.listService.details(LIST_ID_ANIME, sortBy = ListSortBy.VOTE_AVERAGE_DESC).invoke()!!
            .results.map { if (it is TmdbShow.Slim) it.voteAverage else 0.0 }

        assertTrue(votes[0] >= votes[1])

        val releaseDates = TMDb.listService.details(LIST_ID_ANIME_MOVIES, sortBy = ListSortBy.RELEASE_DATE_ASC).invoke()!!
            .results.map { if (it is TmdbMovie.Slim) it.releaseDate else null }

        val first = releaseDates[0]!!.date!!
        val second = releaseDates[1]!!.date!!

        assertTrue(first.isBefore(second))
    }

    @Test
    fun `Create and delete list`() = runBlocking {
        TMDb.accessToken = ACCESS_TOKEN

        val createdListId = TMDb.listService.create("My List", "de", "Desc").invoke()!!.listId
        val details = TMDb.listService.details(createdListId).invoke()!!

        assertEquals("My List", details.name)
        assertEquals("Desc", details.description)
        assertEquals("de", details.languageCode)

        assertTrue(TMDb.listService.delete(createdListId).invoke()!!.success)
    }

    @Test
    fun `Create, update and delete list`() = runBlocking {
        TMDb.accessToken = ACCESS_TOKEN

        val listId = TMDb.listService.create("My List", "de").invoke()!!.listId
        val updateResponse = TMDb.listService.update(
            listId,"My New Name", "New Desc", false
        ).invoke()!!

        assertTrue(updateResponse.success)

        val details = TMDb.listService.details(listId).invoke()!!

        assertEquals(listId, details.id)
        assertEquals("My New Name", details.name)
        assertEquals("New Desc", details.description)
        assertEquals(false, details.public)

        assertTrue(TMDb.listService.delete(listId).invoke()!!.success)
    }
}