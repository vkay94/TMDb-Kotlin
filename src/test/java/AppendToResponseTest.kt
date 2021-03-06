import de.vkay.api.tmdb.AppendToResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AppendToResponseTest {

    @Test
    fun `Test appendItems without special key`() {
        val append = AppendToResponse(AppendToResponse.Item.IMAGES, AppendToResponse.Item.VIDEOS)
        assertEquals("images,videos", append.toString())
    }

    @Test
    fun `Test appendItems with special key`() {
        val append = AppendToResponse(AppendToResponse.Item.WATCH_PROVIDERS)
        assertEquals("watch/providers", append.toString())
    }

//    @Test
//    fun `Test addSeason only`() {
//        val append = AppendToResponse().addSeason(1, 2)
//        assertEquals("season/1,season/2", append.toString())
//    }
//
//    @Test
//    fun `Test addSeason and items`() {
//        val append = AppendToResponse(
//            AppendToResponse.Item.VIDEOS, AppendToResponse.Item.CREDITS
//        ).addSeason(1, 2)
//
//        assertEquals("videos,credits,season/1,season/2", append.toString())
//    }
}