package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class ConfigurationServiceTest : BaseServiceTest() {

    @Test
    fun `Get countries, default`() = runBlocking {
        val countries = TMDb.configurationService.countries().invoke()!!
        assertTrue(countries.isNotEmpty())

        val germany = countries.first { it.countryCode == Locale.GERMANY.country }
        assertEquals("Germany", germany.englishName)
        assertEquals("Germany", germany.nativeName)
    }

    @Test
    fun `Get countries, german language tag`() = runBlocking {
        val countries = TMDb.configurationService.countries(Locale.GERMAN.language).invoke()!!

        val germany = countries.first { it.countryCode == Locale.GERMANY.country }
        assertEquals("Germany", germany.englishName)
        assertEquals("Deutschland", germany.nativeName)

        val us = countries.first { it.countryCode == Locale.US.country }
        assertEquals("United States of America", us.englishName)
        assertEquals("Vereinigte Staaten", us.nativeName)
    }

    @Test
    fun `Get languages`() = runBlocking {
        val languages = TMDb.configurationService.languages().invoke()!!
        assertTrue(languages.isNotEmpty())

        val german = languages.first { it.languageCode == Locale.GERMAN.language }
        assertEquals("German", german.englishName)
        assertEquals("Deutsch", german.name)

        val japanese = languages.first { it.languageCode == Locale.JAPANESE.language }
        assertEquals("Japanese", japanese.englishName)
        assertEquals("日本語", japanese.name)
    }

    @Test
    fun `Get primary translations`() = runBlocking {
        val translations = TMDb.configurationService.primaryTranslations().invoke()!!
        assertTrue(translations.isNotEmpty())

        val german = translations.filter { it.contains("de") }.map { it.split("-")[1] }
        assertTrue(german.containsAll(listOf("DE", "AT")))

        val english = translations.filter { it.contains("en") }.map { it.split("-")[1] }
        assertTrue(english.containsAll(listOf("AU", "CA", "GB", "US")))
    }
}