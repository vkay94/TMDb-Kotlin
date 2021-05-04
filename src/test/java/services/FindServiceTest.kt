package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.ExternalId
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbEpisode
import de.vkay.api.tmdb.models.TmdbPerson
import de.vkay.api.tmdb.models.TmdbShow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class FindServiceTest : BaseServiceTest() {

    @Test
    fun `Find show (Black Clover, IMDB)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.TV, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbShow.Slim) {
            assertEquals(SHOW_ID_BLACK_CLOVER, mediaTypeItem.id)
            assertEquals("Black Clover", mediaTypeItem.title)
        } else {
            throw Exception("MediaTypeItem is not an instance of TmdbShowListObject")
        }
    }

    @Test
    fun `Find show (Black Clover, TVDB)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.TV, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbShow.Slim) {
            assertEquals(SHOW_ID_BLACK_CLOVER, mediaTypeItem.id)
            assertEquals("Black Clover", mediaTypeItem.title)
        } else {
            throw Exception("MediaTypeItem is not an instance of TmdbShowListObject")
        }
    }

    @Test
    fun `Find episode (Black Clover, IMDB)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER_EPISODE_167, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.EPISODE, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbEpisode.Slim) {
            assertEquals(2701437, mediaTypeItem.id)
            assertEquals(167, mediaTypeItem.episodeNumber)
        } else {
            throw Exception("MediaTypeItem is not an instance of TmdbEpisodeListObject")
        }
    }

    @Test
    fun `Find person (Will Smith, IMDB)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_PERSON_WILL_SMITH, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbPerson.Slim) {
            assertEquals("Will Smith", mediaTypeItem.name)
        } else {
            throw Exception("mediaTypeItem is not an instance of TmdbPersonListObject")
        }
    }

    @Test
    fun `Find person (Will Smith, Instagram)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(INSTAGRAM_WILL_SMITH, ExternalId.INSTAGRAM).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbPerson.Slim) {
            assertEquals("Will Smith", mediaTypeItem.name)
        } else {
            throw Exception("mediaTypeItem is not an instance of TmdbPersonListObject")
        }
    }

    @Test
    fun `Find person (Will Smith, Facebook)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(FACEBOOK_PERSON_WILL_SMITH, ExternalId.FACEBOOK).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbPerson.Slim) {
            assertEquals("Will Smith", mediaTypeItem.name)
        } else {
            throw Exception("mediaTypeItem is not an instance of TmdbPersonListObject")
        }
    }

    @Test
    fun `Wrong external source`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER, ExternalId.TWITTER).invoke()
        assertNull(mediaTypeItem)
    }

    @Test
    fun `Invalid id`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find("nenffncnsvoa", ExternalId.IMDB).invoke()
        assertNull(mediaTypeItem)
    }
}