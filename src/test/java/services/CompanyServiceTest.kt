package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CompanyServiceTest : BaseServiceTest() {

    @Test
    fun `Get company details`() = runBlocking {
        val companyId = 3475 // Disney Television Animation
        val details = TMDb.companyService.details(companyId).invoke()!!

        assertEquals("Disney Television Animation", details.name)
        assertEquals("US", details.originCountry)
        assertNotNull(details.logo)
    }

    @Test
    fun `Get company logos`() = runBlocking {
        val companyId = 3475
        val logos = TMDb.companyService.logos(companyId).invoke()!!
        assertEquals(1, logos.size)
    }
}