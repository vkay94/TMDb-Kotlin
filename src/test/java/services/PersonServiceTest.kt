package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.tmdb.TMDb
import de.vkay.tmdb.enumerations.PersonGender
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDate

class PersonServiceTest : BaseServiceTest() {

    @Test
    fun `Get primary data`() = runBlocking {
        val details = TMDb.personService.details(PERSON_ID_WILL_SMITH).invoke()!!

        assertEquals("Will Smith", details.name)
        assertEquals(LocalDate.of(1968, 9,25), details.birthDay?.date)
        assertEquals(PersonGender.MALE, details.gender)
    }

    @Test
    fun `Get translations`() = runBlocking {
        val translations = TMDb.personService.translations(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(translations.isNotEmpty())
    }
}