package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GenreServiceTest : BaseServiceTest() {

    @Test
    fun `Get all TV genres`() = runBlocking {
        val tvGenres = TMDb.genreService.tv().invoke()!!
        assertTrue(tvGenres.isNotEmpty())

        val comedyGenre = tvGenres.first { it.name == "Comedy" }
        assertEquals(35, comedyGenre.id)

        val talkGenre = tvGenres.first { it.name == "Talk" }
        assertEquals(10767, talkGenre.id)

        val tvGenresGerman = TMDb.genreService.tv("de-DE").invoke()!!
        assertTrue(tvGenresGerman.isNotEmpty())

        val animationGenreGerman = tvGenresGerman.first { it.name == "Animation" }
        assertEquals(16, animationGenreGerman.id)

        val familyGenreGerman = tvGenresGerman.first { it.name == "Familie" }
        assertEquals(10751, familyGenreGerman.id)
    }

    @Test
    fun `Get all movie genres`() = runBlocking {
        val movieGenres = TMDb.genreService.movie().invoke()!!
        assertTrue(movieGenres.isNotEmpty())

        val adventureGenre = movieGenres.first { it.name == "Adventure" }
        assertEquals(12, adventureGenre.id)

        val historyGenre = movieGenres.first { it.name == "History" }
        assertEquals(36, historyGenre.id)

        val movieGenresGerman = TMDb.genreService.movie("de-DE").invoke()!!
        assertTrue(movieGenresGerman.isNotEmpty())

        val actionGenreGerman = movieGenresGerman.first { it.name == "Action" }
        assertEquals(28, actionGenreGerman.id)

        val dramaGenreGerman = movieGenresGerman.first { it.name == "Drama" }
        assertEquals(18, dramaGenreGerman.id)
    }
}