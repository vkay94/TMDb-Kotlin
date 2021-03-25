package services

import API_KEY
import com.haroldadmin.cnradapter.invoke
import de.vkay.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

class ConfigurationServiceTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY) }
    }

    @Before
    fun setup() {  }

    @Test
    fun `Get all languages`() = runBlocking {
        val languages = TMDb.configurationService.languages().invoke()!!
        assertTrue(languages.isNotEmpty())
    }
}