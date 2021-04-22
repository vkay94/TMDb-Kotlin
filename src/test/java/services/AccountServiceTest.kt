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
        sessionId = TMDb.accountService.convertToSessionId(ACCESS_TOKEN).invoke()!!.sessionId
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
}