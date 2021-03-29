package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import de.vkay.api.tmdb.models.TmdbDate
import org.threeten.bp.format.DateTimeParseException

/**
 * Reference for valid date string: [Baeldung](https://www.baeldung.com/java-string-valid-date)
 */
internal class TmdbDateJsonAdapter {
    @FromJson
    fun fromJson(json: String?): TmdbDate? {
        return try {
            TmdbDate(json)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    @ToJson
    fun toJson(date: TmdbDate): String {
        return date.toString()
    }
}