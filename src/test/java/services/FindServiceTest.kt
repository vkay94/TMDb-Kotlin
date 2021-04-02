package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import de.vkay.api.tmdb.enumerations.ExternalId
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.TmdbEpisodeListObject
import de.vkay.api.tmdb.models.TmdbPersonListObject
import de.vkay.api.tmdb.models.TmdbShowListObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class FindServiceTest : BaseServiceTest() {

    @Test
    fun `Find show (Black Clover)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.TV, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbShowListObject) {
            assertEquals(SHOW_ID_BLACK_CLOVER, mediaTypeItem.id)
            assertEquals("Black Clover", mediaTypeItem.title)
        } else {
            throw Exception("MediaTypeItem is not an instance of TmdbShowListObject")
        }
    }

    @Test
    fun `Find episode (Black Clover)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_SHOW_BLACK_CLOVER_EPISODE_167, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.EPISODE, mediaTypeItem.mediaType)

        if (mediaTypeItem is TmdbEpisodeListObject) {
            assertEquals(2701437, mediaTypeItem.id)
            assertEquals(167, mediaTypeItem.episodeNumber)
        } else {
            throw Exception("MediaTypeItem is not an instance of TmdbEpisodeListObject")
        }
    }

    @Test
    fun `Find person (Will Smith)`() = runBlocking {
        val mediaTypeItem = TMDb.findService.find(IMDB_PERSON_WILL_SMITH, ExternalId.IMDB).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItem.mediaType)

        val mediaTypeItemFacebook = TMDb.findService.find(FACEBOOK_PERSON_WILL_SMITH, ExternalId.FACEBOOK).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItemFacebook.mediaType)

        val mediaTypeItemInstagram = TMDb.findService.find(INSTAGRAM_WILL_SMITH, ExternalId.INSTAGRAM).invoke()!!
        assertEquals(MediaType.PERSON, mediaTypeItemInstagram.mediaType)

        if (mediaTypeItem is TmdbPersonListObject) {
            assertEquals("Will Smith", mediaTypeItem.name)
        } else {
            throw Exception("mediaTypeItem is not an instance of TmdbPersonListObject")
        }

        if (mediaTypeItemInstagram is TmdbPersonListObject) {
            assertEquals("Will Smith", mediaTypeItemInstagram.name)
        } else {
            throw Exception("mediaTypeItemInstagram is not an instance of TmdbPersonListObject")
        }

        if (mediaTypeItemFacebook is TmdbPersonListObject) {
            assertEquals("Will Smith", mediaTypeItemFacebook.name)
        } else {
            throw Exception("mediaTypeItemFacebook is not an instance of TmdbPersonListObject")
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