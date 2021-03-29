package services

import API_KEY
import de.vkay.api.tmdb.TMDb
import org.junit.BeforeClass

open class BaseServiceTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTMDb() { TMDb.init(API_KEY) }
    }
}