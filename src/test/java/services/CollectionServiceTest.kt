package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbTranslation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CollectionServiceTest : BaseServiceTest() {

    @Test
    fun `Get details`() = runBlocking {
        val details = TMDb.collectionService.details(COLLECTION_ID_AVENGERS, "de-DE").invoke()!!

        assertEquals("Marvel's The Avengers Filmreihe", details.name)
        assertTrue(details.overview.contains("Superhelden wie z. B. Iron Man, Thor, Captain America, Hawkeye,"))
        assertNotNull(details.poster)
        assertNotNull(details.backdrop)
        assertEquals(4, details.movieCount)
    }

    @Test
    fun `Get posters`() = runBlocking {
        val posters = TMDb.collectionService.posters(COLLECTION_ID_AVENGERS).invoke()!!
        assertTrue(posters.isNotEmpty())
    }

    @Test
    fun `Get backdrops`() = runBlocking {
        val backdrops = TMDb.collectionService.backdrops(COLLECTION_ID_AVENGERS).invoke()!!
        assertTrue(backdrops.isNotEmpty())
    }

    @Test
    fun `Get translations`() = runBlocking {
        val translations = TMDb.collectionService.translations(COLLECTION_ID_AVENGERS).invoke()!!
        val german = translations.find { it.languageCode == "de" && it.countryCode == "DE" }!!
        val us = translations.find { it.languageCode == "en" && it.countryCode == "US" }!!

        assertEquals("Marvel's The Avengers Filmreihe", (german.data as TmdbTranslation.Data.Overview).title)
        assertTrue((german.data as TmdbTranslation.Data.Overview).overview.isNotBlank())

        assertTrue((us.data as TmdbTranslation.Data.Overview).title.isBlank())
        assertTrue((us.data as TmdbTranslation.Data.Overview).overview.contains("A superhero film series produced"))
    }
}