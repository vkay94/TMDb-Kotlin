package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ListServiceTest : BaseServiceTest() {

    @Test
    fun `Get list details (public)`() = runBlocking {
        val details = TMDb.listService.details(LIST_ID_ANIME).invoke()!!
        assertEquals("Animes", details.name)
        assertEquals(0, details.revenue)
        assertNotNull(details.background)
        assertNotNull(details.poster)
    }
}