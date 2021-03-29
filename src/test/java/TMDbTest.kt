import de.vkay.api.tmdb.TMDb
import org.junit.Assert.assertEquals
import org.junit.Test

class TMDbTest {

    @Test
    fun `Test links`() {
        assertEquals("https://www.themoviedb.org/tv/123?language=en-US", TMDb.tvLink(123))
        assertEquals("https://www.themoviedb.org/tv/123?language=de-DE", TMDb.tvLink(123, "de-DE"))

        assertEquals("https://www.themoviedb.org/tv/123/season/1?language=en-US", TMDb.seasonLink(123, 1))
        assertEquals("https://www.themoviedb.org/tv/123/season/1?language=fr-FR", TMDb.seasonLink(123, 1, "fr-FR"))

        assertEquals("https://www.themoviedb.org/tv/123/season/1/episode/2?language=en-US", TMDb.episodeLink(123, 1, 2))
        assertEquals("https://www.themoviedb.org/tv/123/season/1/episode/2?language=en-GB", TMDb.episodeLink(123, 1, 2, "en-GB"))

        assertEquals("https://www.themoviedb.org/movie/123?language=en-US", TMDb.movieLink(123))
        assertEquals("https://www.themoviedb.org/movie/123?language=de-DE", TMDb.movieLink(123, "de-DE"))

        assertEquals("https://www.themoviedb.org/person/123?language=en-US", TMDb.personLink(123))
        assertEquals("https://www.themoviedb.org/person/123?language=fr-FR", TMDb.personLink(123, "fr-FR"))
    }
}