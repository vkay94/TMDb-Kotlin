package services

import ACCESS_TOKEN
import ACCOUNT_ID_INT
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbBody
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class AccountServiceTest : BaseServiceTest() {

    var sessionId = ""

    @Before
    fun setup() = runBlocking {
        sessionId = TMDb.accountService.accessTokenToSessionID(ACCESS_TOKEN).invoke()!!.sessionId
        assertTrue(sessionId.isNotBlank())

        TMDb.sessionId = sessionId
    }

    @Test
    fun `Get account`() = runBlocking {
        val details = TMDb.accountService.details().invoke()!!

        assertEquals(Locale.US.language, details.languageCode)
        assertEquals(Locale.GERMANY.country, details.countryCode)
        assertFalse(details.includeAdult)
        assertTrue(details.name.isBlank())
        assertTrue(details.accountId > 0)
    }

    @Test
    fun `Get created lists`() = runBlocking {
        val page = TMDb.accountService.createdLists(ACCOUNT_ID_INT).invoke()!!
        assertEquals(1, page.totalPages)
        assertTrue(page.totalResults >= 2)
    }

    @Test
    fun `Get favorite movies`() = runBlocking {
        val page = TMDb.accountService.favoriteMovies(ACCOUNT_ID_INT).invoke()!!
        assertEquals(1, page.totalPages)
        assertTrue(page.totalResults >= 1)

        val movie = page.results.firstOrNull { it.id == 572154 }
        assertNotNull(movie)
    }

    @Test
    fun `Get favorite tv shows`() = runBlocking {
        val page = TMDb.accountService.favoriteTv(ACCOUNT_ID_INT).invoke()!!
        assertEquals(2, page.totalPages)
        assertTrue(page.totalResults >= 25)
    }

    @Test
    fun `Get account state (movie)`() = runBlocking {
        val stateOne = TMDb.movieService.accountState(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!
        assertFalse(stateOne.isFavorite)
        assertFalse(stateOne.isOnWatchlist)
        assertNull(stateOne.rating)
        assertEquals(MOVIE_ID_AVENGERS_ENDGAME, stateOne.id)

        val stateTwo = TMDb.movieService.accountState(MOVIE_ID_BUNNY_GIRL_SENPAI).invoke()!!
        assertTrue(stateTwo.isFavorite)
        assertFalse(stateTwo.isOnWatchlist)
        assertEquals(10, stateTwo.rating)
    }

    @Test
    fun `Get account state (tv)`() = runBlocking {
        val state = TMDb.showService.accountState(SHOW_ID_BLACK_CLOVER).invoke()!!
        assertTrue(state.isFavorite)
        assertFalse(state.isOnWatchlist)
        assertEquals(10, state.rating)
    }

    @Test
    fun `Get account state (season)`() = runBlocking {
        val seasonStateList = TMDb.seasonService.accountState(SHOW_ID_MHA, 1).invoke()!!
        assertNull(seasonStateList.first().rating)
    }

    @Test
    fun `Get account state (episode)`() = runBlocking {
        val episodeState = TMDb.episodeService.accountState(SHOW_ID_MHA, 1, 1).invoke()!!
        assertNull(episodeState.rating)
    }

    @Test
    fun `Get rated movies`() = runBlocking {
        val ratedMoviePage = TMDb.accountService.ratedMovies(ACCOUNT_ID_INT, "de").invoke()!!
        assertEquals(1, ratedMoviePage.totalPages)
        assertTrue(ratedMoviePage.totalResults > 0)
        assertTrue(ratedMoviePage.results.all { it.rating != null })
    }

    @Test
    fun `Get rated shows`() = runBlocking {
        val ratedShowsPage = TMDb.accountService.ratedShows(ACCOUNT_ID_INT, "de").invoke()!!
        assertTrue(ratedShowsPage.totalPages >= 4)
        assertTrue(ratedShowsPage.totalResults >= 80)
        assertTrue(ratedShowsPage.results.all { it.rating != null })
    }

    @Test
    fun `Get rated episodes`() = runBlocking {
        val ratedShowsPage = TMDb.accountService.ratedEpisodes(ACCOUNT_ID_INT, "de").invoke()!!
        assertTrue(ratedShowsPage.totalResults == 0)
    }

    @Test
    fun `Mark as favorite`() = runBlocking {
        val addFavoriteBody = TmdbBody.MarkFavorite(MediaType.MOVIE, MOVIE_ID_AVENGERS_ENDGAME, true)
        val removeFavoriteBody = TmdbBody.MarkFavorite(MediaType.MOVIE, MOVIE_ID_AVENGERS_ENDGAME, false)

        val add = TMDb.accountService.markFavorite(ACCOUNT_ID_INT, addFavoriteBody).invoke()!!
        val stateOne = TMDb.movieService.accountState(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        val remove = TMDb.accountService.markFavorite(ACCOUNT_ID_INT, removeFavoriteBody).invoke()!!
        val stateTwo = TMDb.movieService.accountState(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        assertTrue(add.success!!)
        assertTrue(remove.success!!)
        assertTrue(stateOne.isFavorite)
        assertFalse(stateTwo.isFavorite)
    }

    @Test
    fun `Add to watchlist`() = runBlocking {
        val addBody = TmdbBody.AddToWatchlist(MediaType.MOVIE, MOVIE_ID_AVENGERS_ENDGAME, true)
        val removeBody = TmdbBody.AddToWatchlist(MediaType.MOVIE, MOVIE_ID_AVENGERS_ENDGAME, false)

        val add = TMDb.accountService.addToWatchlist(ACCOUNT_ID_INT, addBody).invoke()!!
        val stateOne = TMDb.movieService.accountState(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        val remove = TMDb.accountService.addToWatchlist(ACCOUNT_ID_INT, removeBody).invoke()!!
        val stateTwo = TMDb.movieService.accountState(MOVIE_ID_AVENGERS_ENDGAME).invoke()!!

        assertTrue(add.success!!)
        assertTrue(remove.success!!)
        assertTrue(stateOne.isOnWatchlist)
        assertFalse(stateTwo.isOnWatchlist)
    }
}