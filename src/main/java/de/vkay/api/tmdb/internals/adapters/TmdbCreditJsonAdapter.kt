package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.internals.EnumValueJsonAdapter
import de.vkay.api.tmdb.internals.hasKeys
import de.vkay.api.tmdb.models.TmdbCredit
import de.vkay.api.tmdb.models.TmdbCredit_CastJsonAdapter
import de.vkay.api.tmdb.models.TmdbCredit_CrewJsonAdapter
import de.vkay.api.tmdb.models.TmdbCredit_GuestJsonAdapter

internal class TmdbCreditJsonAdapter : JsonAdapter<TmdbCredit>() {
    private val moshi = Moshi.Builder()
        .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
            .withUnknownFallback(PersonGender.OTHER))
        .build()

    private val crewJsonAdapter = TmdbCredit_CrewJsonAdapter(moshi)
    private val castJsonAdapter = TmdbCredit_CastJsonAdapter(moshi)
    private val guestJsonAdapter = TmdbCredit_GuestJsonAdapter(moshi)

    override fun fromJson(reader: JsonReader): TmdbCredit {
        // Crew: has "job"
        // Cast:
        // Guest: has character_name

        return if (reader.hasKeys(true, "job")) {
            crewJsonAdapter.fromJson(reader)
        } else if (reader.hasKeys(true, "character_name")) {
            guestJsonAdapter.fromJson(reader)
        } else {
            castJsonAdapter.fromJson(reader)
        }
    }

    override fun toJson(writer: JsonWriter, value: TmdbCredit?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}