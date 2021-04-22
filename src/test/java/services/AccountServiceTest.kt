package services

import ACCESS_TOKEN
import ACCOUNT_ID_INT
import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
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
        assertFalse(stateOne.favorite)
        assertFalse(stateOne.watchlist)
        assertEquals(-1, stateOne.rating)
        assertEquals(MOVIE_ID_AVENGERS_ENDGAME, stateOne.id)

        val stateTwo = TMDb.movieService.accountState(MOVIE_ID_BUNNY_GIRL_SENPAI).invoke()!!
        assertTrue(stateTwo.favorite)
        assertFalse(stateTwo.watchlist)
        assertEquals(10, stateTwo.rating)
    }

    @Test
    fun `Get account state (tv)`() = runBlocking {
        val state = TMDb.showService.accountState(SHOW_ID_BLACK_CLOVER).invoke()!!
        assertTrue(state.favorite)
        assertFalse(state.watchlist)
        assertEquals(10, state.rating)
    }

    @Test
    fun `Get account state (season)`() = runBlocking {
        val seasonStateList = TMDb.seasonService.accountState(SHOW_ID_MHA, 1).invoke()!!
        assertTrue(seasonStateList.first().rating == -1)
    }

    @Test
    fun `Get account state (episode)`() = runBlocking {
        val episodeState = TMDb.episodeService.accountState(SHOW_ID_MHA, 1, 1).invoke()!!
        assertEquals(-1, episodeState.rating)
    }
}