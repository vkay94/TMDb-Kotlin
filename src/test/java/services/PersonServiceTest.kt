package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
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
        val movieCast = TMDb.personService.movieCast(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(movieCast.isNotEmpty())

        val (movie, roleJob) = movieCast.find { it.first.id == 602 }!!
        assertEquals("Independence Day", movie.title)
        assertEquals("Capt. Steven Hiller", roleJob.jobCharacter)
        assertEquals("52fe425bc3a36847f8017f8b", roleJob.creditId)
        assertEquals(null, roleJob.episodeCount)
    }

    @Test
    fun `Get tv credits`() = runBlocking {
        val tvCrew = TMDb.personService.tvCrew(PERSON_ID_WILL_SMITH).invoke()!!
        assertTrue(tvCrew.isNotEmpty())

        val (show, roleJob) = tvCrew.find { it.first.id == 1566 }!! /* All of Us */
        assertEquals("All of Us", show.title)
        assertEquals("Director", roleJob.jobCharacter)
        assertEquals("52570ce219c29571140186c5", roleJob.creditId)
        assertEquals(1, roleJob.episodeCount)
    }

    @Test
    fun `Get combined credits`() = runBlocking {
        val combinedCast = TMDb.personService.combinedCast(PERSON_ID_WILL_SMITH).invoke()!!
        val shows = combinedCast.filterIsInstance<TmdbShow.Slim>()
        assertNotNull(shows.find { it.id == 1514 }) /* The One Show */
    }
}