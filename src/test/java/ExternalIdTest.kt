import de.vkay.api.tmdb.enumerations.ExternalId
import org.junit.Assert.assertEquals
import org.junit.Test

class ExternalIdTest {

    @Test
    fun `Test ExternalId toString`() {
        assertEquals("imdb_id", ExternalId.IMDB.toString())
        assertEquals("tvdb_id", ExternalId.TVDB.toString())
        assertEquals("facebook_id", ExternalId.FACEBOOK.toString())
        assertEquals("instagram_id", ExternalId.INSTAGRAM.toString())
        assertEquals("twitter_id", ExternalId.TWITTER.toString())
    }
}