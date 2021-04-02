package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkServiceTest : BaseServiceTest() {

    @Test
    fun `Get network details`() = runBlocking {
        val networkId = 213
        val details = TMDb.networkService.details(networkId).invoke()!!
        assertEquals("Netflix", details.name)
    }

    @Test
    fun `Get network logos`() = runBlocking {
        val networkId = 213
        val logos = TMDb.networkService.logos(networkId).invoke()!!
        assertEquals(1, logos.size)
    }
}