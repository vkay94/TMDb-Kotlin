package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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

    @Test
    fun `Get movie credits`() = runBlocking {
        val movieCrew = TMDb.personService.movieCrew(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(movieCrew.isNotEmpty())
        assertNotNull(movieCrew.find { it.id == 1402 }) /* The Pursuit of Happyness */

        val movieCast = TMDb.personService.movieCast(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(movieCast.isNotEmpty())
        assertNotNull(movieCast.find { it.id == 602 }) /* Independence Day */
    }

    @Test
    fun `Get tv credits`() = runBlocking {
        val tvCrew = TMDb.personService.tvCrew(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(tvCrew.isNotEmpty())
        assertNotNull(tvCrew.find { it.id == 1566 }) /* All of Us */

        val tvCast = TMDb.personService.tvCast(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(tvCast.isNotEmpty())
        assertNotNull(tvCast.find { it.id == 1514 }) /* The One Show */
    }

    @Test
    fun `Get combined credits`() = runBlocking {
        val combinedCast = TMDb.personService.combinedCast(PERSON_ID_WILL_SMITH).invoke()!!
        val shows = combinedCast.filterIsInstance<TmdbShow.Slim>()
        assertNotNull(shows.find { it.id == 1514 }) /* The One Show */
    }
}