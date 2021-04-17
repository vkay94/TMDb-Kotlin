package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbTranslationData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate

class TvEpisodeServiceTest : BaseServiceTest() {

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
        assertNotNull(details.still)
        assertEquals(LocalDate.of(2016, 4, 3), details.releaseDate?.date)
    }

    @Test
    fun `Get additional data`() = runBlocking {
        val season = 1
        val episode = 1
        val appendToResponse = AppendToResponse(AppendToResponse.Item.IMAGES, AppendToResponse.Item.CREDITS)

        val details = TMDb.episodeService.details(SHOW_ID_MHA, season, episode, appendToResponse = appendToResponse).invoke()!!

        assertFalse(details.stills.isEmpty())
        assertTrue(details.crew.isEmpty())
        assertTrue(details.cast.isNotEmpty())
    }

    @Test
    fun `Get stills`() = runBlocking {
        val season = 1
        val episode = 1

        val stills = TMDb.episodeService.stills(SHOW_ID_MHA, season, episode).invoke()!!
        assertFalse(stills.isEmpty())

        val firstImage = stills.firstOrNull()
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

        assertEquals("tt5632458", externalIds.imdb)
        assertEquals(5465180, externalIds.tvdb)
        assertNull(externalIds.twitter)
        assertNull(externalIds.facebook)
        assertNull(externalIds.instagram)
    }

    @Test
    fun `Get crew`() = runBlocking {
        val season = 1
        val episode = 1

        val crew = TMDb.episodeService.crew(SHOW_ID_MHA, season, episode).invoke()!!
        assertTrue(crew.isEmpty())
    }

    @Test
    fun `Get cast`() = runBlocking {
        val season = 1
        val episode = 1

        val cast = TMDb.episodeService.cast(SHOW_ID_MHA, season, episode).invoke()!!
        assertTrue(cast.isNotEmpty())
    }

    @Test
    fun `Get guest stars`() = runBlocking {
        val season = 1
        val episode = 1

        val guests = TMDb.episodeService.guestStars(SHOW_ID_MHA, season, episode).invoke()!!
        assertFalse(guests.isEmpty())
    }
}