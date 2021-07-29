package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WatchProvidersServiceTest : BaseServiceTest() {

    @Test
    fun `Get TV providers`() = runBlocking {
        val providers = TMDb.watchProvidersService.tv().invoke()!!
        assertTrue(providers.isNotEmpty())

        val crunchyroll = providers.find { it.name == "Crunchyroll" }!!
        assertEquals(283, crunchyroll.id)
        assertNotNull(crunchyroll.logo)
    }

    @Test
    fun `Get TV providers with region`() = runBlocking {
        val all = TMDb.watchProvidersService.tv().invoke()!!
        val germany = TMDb.watchProvidersService.tv(region = "DE").invoke()!!

        val hboProvider = all.find { it.name == "HBO" } // American provider
        assertNull(germany.find { it == hboProvider })
    }

    @Test
    fun `Get movie providers`() = runBlocking {
        val providers = TMDb.watchProvidersService.movie().invoke()!!
        assertTrue(providers.isNotEmpty())
    }
}