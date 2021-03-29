package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class KeywordServiceTest : BaseServiceTest() {

    @Test
    fun `Get keyword details`() = runBlocking {
        val keywordId = 3417
        val keywordDetails = TMDb.keywordService.details(keywordId).invoke()!!
        assertEquals("wormhole", keywordDetails.name)
    }
}