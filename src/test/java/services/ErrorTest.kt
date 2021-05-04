package services

import com.haroldadmin.cnradapter.NetworkResponse
import de.vkay.api.tmdb.TMDb
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ErrorTest {

    @Test
    fun `Invalid API key`() = runBlocking {
        TMDb.init("INVALID_KEY")
        when (val response = TMDb.showService.externalIds(SHOW_ID_MHA)) {
            is NetworkResponse.ServerError -> {
                assertEquals(401, response.code)
                val errorResponse = response.body

                assertEquals(7, errorResponse?.code)
                assertEquals("Invalid API key: You must be granted a valid key.", errorResponse?.message)
            }
        }
    }
}