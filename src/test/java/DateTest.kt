import de.vkay.api.tmdb.models.TmdbDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.FormatStyle
import java.util.*

class DateTest {

    @Test
    fun `Test constructors`() {
        val expectedLocalDate = LocalDate.of(2020, 5, 15)
        val dateFromString = TmdbDate("2020-05-15")
        val dateFromLocalDateTime = TmdbDate(LocalDateTime.of(2020, 5, 15, 10, 50))

        assertEquals(expectedLocalDate, dateFromString.date)
        assertEquals(expectedLocalDate, dateFromLocalDateTime.date)
    }

    @Test
    fun `Test localize`() {
        val date = TmdbDate("2020-05-15")

        assertEquals("May 15, 2020", date.localize())
        assertEquals("15. Mai 2020", date.localize(Locale.GERMAN))
        assertEquals("15. Mai 2020", date.localize(Locale.GERMANY))

        assertEquals("15.05.20", date.localize(Locale.GERMANY, FormatStyle.SHORT))
        assertEquals("15.05.2020", date.localize(Locale.GERMANY, FormatStyle.MEDIUM))
    }

    @Test
    fun `Test sort by date`() {
        val firstDate = TmdbDate("2020-05-15")
        val secondDate = TmdbDate("2020-08-15")
        val thirdDate = TmdbDate("2020-09-15")
        val invalidDate = TmdbDate("Invalid date string")

        val orderedList = listOf(firstDate, secondDate, thirdDate)
        val unorderedList = listOf(thirdDate, firstDate, secondDate)
        val nullList = listOf(firstDate, thirdDate, invalidDate, secondDate, invalidDate)

        val sorted = unorderedList.sorted()
        assertEquals(orderedList, sorted)

        val sortedBy = unorderedList.sortedBy { it.date }
        assertEquals(orderedList, sortedBy)

        val sortedWithNull = nullList.filter { it.date != null }.sorted()
        assertEquals(orderedList, sortedWithNull)
    }
}