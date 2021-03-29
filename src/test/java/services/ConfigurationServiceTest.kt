package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class ConfigurationServiceTest : BaseServiceTest() {

    @Test
    fun `Get all languages`() = runBlocking {
        val languages = TMDb.configurationService.languages().invoke()!!
        assertTrue(languages.isNotEmpty())
    }
}