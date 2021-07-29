import de.vkay.api.tmdb.IncludeLanguages
import de.vkay.api.tmdb.models.TmdbImage
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImageTest {

    @Test
    fun `Test ImageLanguages toString`() {
        val languageCodesArray = arrayOf("en", "de", "fr")
        val imageLanguageArray = IncludeLanguages(*languageCodesArray)
        val imageLanguageVararg = IncludeLanguages("en", "de", "fr")

        val expected = "en,de,fr"
        assertEquals(expected, imageLanguageArray.toString())
        assertEquals(expected, imageLanguageVararg.toString())
        assertEquals("null,en", IncludeLanguages.defaults.toString())
    }

    @Test
    fun `Test TMDbImage`() {
        val image = TmdbImage("/path/to/image")

        assertEquals("/path/to/image", image.filePath)
        assertEquals(0, image.height)
        assertEquals(0, image.width)
        assertEquals(null, image.languageCode)
    }

    @Test
    fun `Test TMDbImage get method and Quality`() {
        val image = TmdbImage("/path/to/image")
        assertEquals("https://image.tmdb.org/t/p/original/path/to/image", image.get())
        assertEquals("https://image.tmdb.org/t/p/w92/path/to/image", image.get(TmdbImage.Quality.POSTER_W_92))
        assertEquals("https://image.tmdb.org/t/p/w185/path/to/image", image.get(TmdbImage.Quality.POSTER_W_185))
        assertEquals("https://image.tmdb.org/t/p/w300/path/to/image", image.get(TmdbImage.Quality.STILL_W_300))
        assertEquals("https://image.tmdb.org/t/p/w500/path/to/image", image.get(TmdbImage.Quality.POSTER_W_500))
        assertEquals("https://image.tmdb.org/t/p/w780/path/to/image", image.get(TmdbImage.Quality.BACKDROP_W_780))
        assertEquals("https://image.tmdb.org/t/p/w1280/path/to/image", image.get(TmdbImage.Quality.BACKDROP_W_1280))
    }
}