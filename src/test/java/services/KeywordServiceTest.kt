package services

import API_KEY
import com.haroldadmin.cnradapter.invoke
import de.vkay.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class KeywordServiceTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY) }
    }

    @Before
    fun setup() {  }

    @Test
    fun `Get keyword details`() = runBlocking {
        val keywordId = 3417
        val keywordDetails = TMDb.keywordService.details(keywordId).invoke()!!
        assertEquals("wormhole", keywordDetails.name)
    }
}