import de.vkay.tmdb.ImageLanguages
import de.vkay.tmdb.models.TmdbImage
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageTest {

    @Test
    fun `Test ImageLanguages toString`() {
        val languageCodesArray = arrayOf("en", "de", "fr")
        val imageLanguageArray = ImageLanguages(*languageCodesArray)
        val imageLanguageVararg = ImageLanguages("en", "de", "fr")

        val expected = "en,de,fr"
        assertEquals(expected, imageLanguageArray.toString())
        assertEquals(expected, imageLanguageVararg.toString())
        assertEquals("null,en", ImageLanguages.defaults.toString())
    }

    @Test
    fun `Test TMDbImage`() {
        val imageFull = TmdbImage("/path/to/image", 100, 200, "en")
        val imageSecondConstructor = TmdbImage("/path/to/image")

        assertEquals("/path/to/image", imageFull.filePath)
        assertEquals(100, imageFull.height)
        assertEquals(200, imageFull.width)
        assertEquals("en", imageFull.languageCode)

        assertEquals("/path/to/image", imageSecondConstructor.filePath)
        assertEquals(0, imageSecondConstructor.height)
        assertEquals(0, imageSecondConstructor.width)
        assertEquals(null, imageSecondConstructor.languageCode)
    }

    @Test
    fun `Test TMDbImage get method and Quality`() {
        val image = TmdbImage("/path/to/image")
        assertEquals("https://image.tmdb.org/t/p/w500/path/to/image", image.get())
        assertEquals("https://image.tmdb.org/t/p/w92/path/to/image", image.get(TmdbImage.Quality.W_92))
        assertEquals("https://image.tmdb.org/t/p/w185/path/to/image", image.get(TmdbImage.Quality.W_185))
        assertEquals("https://image.tmdb.org/t/p/w300/path/to/image", image.get(TmdbImage.Quality.W_300))
        assertEquals("https://image.tmdb.org/t/p/w500/path/to/image", image.get(TmdbImage.Quality.W_500))
        assertEquals("https://image.tmdb.org/t/p/w780/path/to/image", image.get(TmdbImage.Quality.W_780))
        assertEquals("https://image.tmdb.org/t/p/w1280/path/to/image", image.get(TmdbImage.Quality.W_1280))
        assertEquals("https://image.tmdb.org/t/p/original/path/to/image", image.get(TmdbImage.Quality.ORIGINAL))
    }
}