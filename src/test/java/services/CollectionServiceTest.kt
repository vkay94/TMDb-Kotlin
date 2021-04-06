package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.models.TmdbTranslationData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class CollectionServiceTest : BaseServiceTest() {

    @Test
    fun `Get details`() = runBlocking {
        val details = TMDb.collectionService.details(COLLECTION_ID_AVENGERS, "de-DE").invoke()!!

        assertEquals("Marvel's The Avengers Filmreihe", details.name)
        assertTrue(details.overview.contains("Superhelden wie z. B. Iron Man, Thor, Captain America, Hawkeye,"))
        assertNotNull(details.poster)
        assertNotNull(details.background)
        assertEquals(4, details.movieCount)
    }

    @Test
    fun `Get images`() = runBlocking {
        val images = TMDb.collectionService.images(COLLECTION_ID_AVENGERS).invoke()!!

        assertTrue(images.posters.isNotEmpty())
        assertTrue(images.backdrops.isNotEmpty())
    }

    @Test
    fun `Get translations`() = runBlocking {
        val translations = TMDb.collectionService.translations(COLLECTION_ID_AVENGERS).invoke()!!
        val german = translations.find { it.languageCode == "de" && it.countryCode == "DE" }!!
        val us = translations.find { it.languageCode == "en" && it.countryCode == "US" }!!

        assertEquals("Marvel's The Avengers Filmreihe", (german.data as TmdbTranslationData.Overview).title)
        assertTrue((german.data as TmdbTranslationData.Overview).overview.isNotBlank())

        assertTrue((us.data as TmdbTranslationData.Overview).title.isBlank())
        assertTrue((us.data as TmdbTranslationData.Overview).overview.contains("A superhero film series produced"))
    }
}