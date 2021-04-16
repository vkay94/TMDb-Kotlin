package services

import com.haroldadmin.cnradapter.invoke
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

class CertificationServiceTest : BaseServiceTest() {

    @Test
    fun `Get tv certifications`(): Unit = runBlocking {
        val list = TMDb.certificationService.tv().invoke()!!

        assertTrue(list[Locale.GERMANY.country]!!.isNotEmpty())

        val germany = list[Locale.GERMANY.country]!!
        assertEquals(5, germany.size)
        assertEquals(listOf(0, 6, 12, 16, 18), germany.map { it.certification.toInt() }.sorted())
    }

    @Test
    fun `Get movie certifications`(): Unit = runBlocking {
        val list = TMDb.certificationService.movie().invoke()!!

        assertTrue(list[Locale.US.country]!!.isNotEmpty())
        assertEquals(6, list["US"]!!.count())

    }
}