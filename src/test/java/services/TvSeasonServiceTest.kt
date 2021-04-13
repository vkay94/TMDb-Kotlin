package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.AppendToResponse
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.LocalDate

class TvSeasonServiceTest : BaseServiceTest() {

    @Test
    fun `Get primary data`() = runBlocking {
        val season = 1
        val details = TMDb.seasonService.details(SHOW_ID_HORIMIYA, season).invoke()!!

        assertEquals(LocalDate.of(2021, 1, 10), details.premierDate!!.date)
        assertEquals(1, details.seasonNumber)
        assertTrue(details.episodes.size > 4)
        assertNotNull(details.poster)
    }

    @Test
    fun `Get additional data`() = runBlocking {
        val season = 1
        val appendToResponse = AppendToResponse(
            AppendToResponse.Item.IMAGES,
            AppendToResponse.Item.VIDEOS
        )

        val details = TMDb.seasonService.details(SHOW_ID_TBBT, season, append=appendToResponse).invoke()!!
        assertTrue(details.posters.isNotEmpty())
        assertTrue(details.videos.isNotEmpty())
    }

    @Test
    fun `Get posters`() = runBlocking {
        val season = 1
        val posters = TMDb.seasonService.posters(SHOW_ID_TBBT, season).invoke()!!
        assertTrue(posters.isNotEmpty())
    }

    @Test
    fun `Get translations`() = runBlocking {
        val season = 1
        val translations = TMDb.seasonService.translations(SHOW_ID_MHA, season).invoke()!!
        assertTrue(translations.isNotEmpty())
    }

    @Test
    fun `Get external ids`() = runBlocking {
        val season = 1
        val externalIds = TMDb.seasonService.externalIds(SHOW_ID_MHA, season).invoke()!!

        assertEquals(651039, externalIds.tvdb)
        assertNull(externalIds.twitter)
        assertNull(externalIds.facebook)
        assertNull(externalIds.instagram)
    }
}