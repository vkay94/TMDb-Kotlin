package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.Discover
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDate

class DiscoverServiceShowTest : BaseServiceTest() {

    @Test
    fun `Test sortBy`() = runBlocking {
        val builder = Discover.ShowBuilder()
            .sortBy(Discover.SortBy.POPULARITY_DESC)

        val result = TMDb.discoverService.tv(builder).invoke()!!
        assertTrue(result.results[0].popularity > result.results[1].popularity)
    }

    @Test
    fun `Test first air date`() = runBlocking {
        val upper = LocalDate.of(2021, 3, 20)
        val lower = LocalDate.of(2021, 2, 20)

        val builder = Discover.ShowBuilder()
            .firstAirDateGreaterEqual(lower)
            .firstAirDateLessEqual(upper)

        val results = TMDb.discoverService.tv(builder).invoke()!!.results
        results.forEach {
            assertTrue(it.releaseDate!!.date!!.isAfter(lower.minusDays(1))
                    && it.releaseDate!!.date!!.isBefore(upper.plusDays(1)))
        }
    }

    @Test
    fun `Test with networks`() = runBlocking {
        val networkIdTokyoMx = 614
        val networkIdCrunchyroll = 1112

        val builderSingle = Discover.ShowBuilder()
            .withNetworks(listOf(networkIdCrunchyroll))

        val resultSingle = TMDb.discoverService.tv(builderSingle).invoke()!!
        assertTrue(resultSingle.totalResults > 15)

        val builderMultiple = Discover.ShowBuilder()
            .withNetworks(listOf(networkIdTokyoMx, networkIdCrunchyroll))

        val resultMultiple = TMDb.discoverService.tv(builderMultiple).invoke()!!
        assertTrue(resultMultiple.totalResults > 1)
        assertEquals("Tower of God", resultMultiple.results.first().title)
    }
}