package de.vkay.api.tmdb.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle
import java.util.*

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/a/40920186/5199098)
 */
class TmdbDate constructor(val date: LocalDate?) : Comparable<TmdbDate> {
    constructor(str: String?) : this(validate(str))
    constructor(localDateTime: LocalDateTime?) : this(localDateTime?.toLocalDate())

    /**
     * Returns a human string depending on device location in form of [FormatStyle.LONG]
     *
     * For example: 15. Mai 2020 (Germany)
     */
    fun localize(locale: Locale = Locale.US, format: FormatStyle = FormatStyle.LONG): String? {
        return date?.format(DateTimeFormatter.ofLocalizedDate(format).withLocale(locale))
    }

    // For orderBy
    override fun compareTo(other: TmdbDate): Int {
        if (this.date == null || other.date == null)
            return 0

        return this.date.compareTo(other.date)
    }

    override fun toString(): String = date.toString()

    companion object {

        private fun validate(str: String?): LocalDate? {
            return try {
                LocalDate.parse(str?.split("T")?.first())
            } catch (e: DateTimeParseException) {
                null
            }
        }

        internal val ADAPTER = object : Any() {
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
    }
}