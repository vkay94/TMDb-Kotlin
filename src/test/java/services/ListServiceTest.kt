package services

import ACCESS_TOKEN
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.ListSortBy
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbBody
import de.vkay.api.tmdb.models.TmdbMovie
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*


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
        val createListBody = TmdbBody.CreateList("My List", Locale.GERMAN.language)
        val listId = TMDb.listService.create(createListBody).invoke()!!.listId

        val details = TMDb.listService.details(listId).invoke()!!

        assertEquals("My List", details.name)
        assertTrue(details.description.isBlank())
        assertTrue(details.public)
        assertEquals(Locale.GERMAN.language, details.languageCode)

        assertTrue(TMDb.listService.delete(listId).invoke()!!.success!!)
    }

    @Test
    fun `Create, update and delete list`() = runBlocking {
        TMDb.accessToken = ACCESS_TOKEN

        val createListBody = TmdbBody.CreateList("My List", Locale.GERMAN.language)
        val updateListBody = TmdbBody.UpdateList(
            ListSortBy.VOTE_AVERAGE_DESC,
            name = "New Name",
            description = "Added Description",
            public = false
        )

        val listId = TMDb.listService.create(createListBody).invoke()!!.listId
        val update = TMDb.listService.update(listId, updateListBody).invoke()!!

        val details = TMDb.listService.details(listId).invoke()!!

        assertEquals("New Name", details.name)
        assertEquals("Added Description", details.description)
        assertEquals(ListSortBy.VOTE_AVERAGE_DESC, details.sortBy)
        assertFalse(details.public)

        assertTrue(TMDb.listService.delete(listId).invoke()!!.success!!)
    }

    @Test
    fun `Add items to list`() = runBlocking {
        TMDb.accessToken = ACCESS_TOKEN

        val createListBody = TmdbBody.CreateList("My Sample List", "de")
        val listId = TMDb.listService.create(createListBody).invoke()!!.listId

        val details1 = TMDb.listService.details(listId).invoke()!!
        assertEquals(0, details1.totalResults)

        val addBuilder1 = TmdbBody.MediaItem.Builder()
            .addItem(MediaType.TV, SHOW_ID_MHA)
            .addItem(MediaType.TV, SHOW_ID_BLACK_CLOVER)

        val addedItemsResponseList1 = TMDb.listService.addItems(listId, addBuilder1).invoke()!!
        assertEquals(2, addedItemsResponseList1.count())
        assertTrue(addedItemsResponseList1.all { it.success == true })

        val addBuilder2 = TmdbBody.MediaItem.Builder()
            .addItem(MediaType.TV, SHOW_ID_MHA)

        val addedItemsResponseList2 = TMDb.listService.addItems(listId, addBuilder2).invoke()!!
        assertEquals(1, addedItemsResponseList2.count())
        assertFalse(addedItemsResponseList2.first().success!!)

        val details2 = TMDb.listService.details(listId).invoke()!!
        assertEquals(2, details2.totalResults)

        val del = TMDb.listService.delete(listId)
        assertTrue(del.invoke()!!.success!!)
    }

    @Test
    fun `Remove items from list`() = runBlocking {
        TMDb.accessToken = ACCESS_TOKEN

        val createListBody = TmdbBody.CreateList("My Sample List", "de")
        val listId = TMDb.listService.create(createListBody).invoke()!!.listId

        val addBuilder = TmdbBody.MediaItem.Builder()
            .addItem(MediaType.TV, SHOW_ID_MHA)
            .addItem(MediaType.TV, SHOW_ID_BLACK_CLOVER)

        val addedItemsResponseList1 = TMDb.listService.addItems(listId, addBuilder).invoke()!!
        assertEquals(2, addedItemsResponseList1.count())
        assertTrue(addedItemsResponseList1.all { it.success == true })

        val removeBuilder = TmdbBody.MediaItem.Builder()
            .addItem(MediaType.TV, SHOW_ID_MHA)

        val removeItemsResponseList = TMDb.listService.removeItems(listId, removeBuilder).invoke()!!
        assertEquals(1, removeItemsResponseList.count())
        assertTrue(removeItemsResponseList.first().success!!)

        val details2 = TMDb.listService.details(listId).invoke()!!
        assertEquals(1, details2.totalResults)

        val del = TMDb.listService.delete(listId)
        assertTrue(del.invoke()!!.success!!)
    }
}