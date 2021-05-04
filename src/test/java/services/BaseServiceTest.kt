package services

import API_KEY
import V4_AUTH
import de.vkay.api.tmdb.TMDb
import org.junit.jupiter.api.BeforeAll

open class BaseServiceTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY, V4_AUTH) }
    }
}