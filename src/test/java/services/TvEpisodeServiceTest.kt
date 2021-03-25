package services

import API_KEY
import com.haroldadmin.cnradapter.invoke
import de.vkay.tmdb.AppendToResponse
import de.vkay.tmdb.TMDb
import de.vkay.tmdb.models.TmdbTranslationData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.threeten.bp.LocalDate

class TvEpisodeServiceTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY) }
    }

    @Before
    fun setup() {  }

    @Test
    fun `Get primary data`() = runBlocking {
        val season = 1
        val episode = 1
        val language = "de"

        val details = TMDb.episodeService.details(SHOW_ID_MHA, season, episode, language).invoke()!!

        assertEquals("Midoriya Izuku: Origin", details.title)
        assertEquals(season, details.seasonNumber)
        assertEquals(episode, details.episodeNumber)
        assertTrue(details.overview.isNotBlank())
        assertNotNull(details.background)
        assertEquals(LocalDate.of(2016, 4, 3), details.releaseDate?.date)
    }

    @Test
    fun `Get additional data`() = runBlocking {
        val season = 1
        val episode = 1
        val appendToResponse = AppendToResponse(AppendToResponse.Item.IMAGES, AppendToResponse.Item.CREDITS)

        val details = TMDb.episodeService.details(SHOW_ID_MHA, season, episode, appendToResponse = appendToResponse).invoke()!!

        assertFalse(details.backgrounds.isEmpty())
        assertTrue(details.crew.isEmpty())
        assertTrue(details.cast.isNotEmpty())
    }

    @Test
    fun `Get images`() = runBlocking {
        val season = 1
        val episode = 1

        val images = TMDb.episodeService.images(SHOW_ID_MHA, season, episode).invoke()!!
        assertFalse(images.stills.isEmpty())

        val firstImage = images.stills.firstOrNull()
        assertNotNull(firstImage)
        assertNotEquals(0, firstImage?.height)
        assertNotEquals(0, firstImage?.width)
    }

    @Test
    fun `Get translations`() = runBlocking {
        val season = 1
        val episode = 1
        val translations = TMDb.episodeService.translations(SHOW_ID_MHA, season, episode).invoke()!!
        assertTrue(translations.isNotEmpty())

        val germanTranslation = translations.first { it.languageCode == "de" }
        assertTrue((germanTranslation.data as TmdbTranslationData.Overview).overview.contains(
            "Izuku Midoriya hat leider keine besondere angeborene"
        ))
        assertEquals("Deutsch", germanTranslation.name)
        assertEquals("German", germanTranslation.englishName)
    }

    @Test
    fun `Get external ids`() = runBlocking {
        val season = 1
        val episode = 1
        val externalIds = TMDb.episodeService.externalIds(SHOW_ID_MHA, season, episode).invoke()!!

        assertNull(externalIds.tvdb)
        assertNull(externalIds.twitter)
        assertNull(externalIds.facebook)
        assertNull(externalIds.instagram)
    }

    @Test
    fun `Get credits`() = runBlocking {
        val season = 1
        val episode = 1
        val credits = TMDb.episodeService.credits(SHOW_ID_MHA, season, episode).invoke()!!

        assertTrue(credits.cast.isNotEmpty())
        assertTrue(credits.crew.isEmpty())
    }
}