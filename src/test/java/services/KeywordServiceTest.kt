package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KeywordServiceTest : BaseServiceTest() {

    @Test
    fun `Get keyword details`() = runBlocking {
        val keywordId = 3417
        val keywordDetails = TMDb.keywordService.details(keywordId).invoke()!!
        assertEquals("wormhole", keywordDetails.name)
    }
}