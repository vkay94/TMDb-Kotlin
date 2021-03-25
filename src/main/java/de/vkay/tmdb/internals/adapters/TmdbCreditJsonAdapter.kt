package de.vkay.tmdb.internals.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import de.vkay.tmdb.enumerations.PersonGender
import de.vkay.tmdb.internals.EnumValueJsonAdapter
import de.vkay.tmdb.models.TmdbCredit

internal class TmdbCreditJsonAdapter : JsonAdapter<TmdbCredit>() {
    private val moshi = Moshi.Builder()
        .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
            .withUnknownFallback(PersonGender.OTHER))
        .build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "id", "credit_id", "name", "original_name", "gender", "adult", "profile_path",
        "known_for_department", "character", "character_name", "job"
    )

    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
        "name")

    private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java, emptySet(),
        "_profilePath")

    private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java, emptySet(), "id")

    private val nullablePersonGenderAdapter: JsonAdapter<PersonGender?> =
        moshi.adapter(PersonGender::class.java, emptySet(), "gender")

    private val booleanAdapter: JsonAdapter<Boolean> = moshi.adapter(Boolean::class.java, emptySet(),
        "adult")

    override fun fromJson(reader: JsonReader): TmdbCredit {
        var personId: Int? = null
        var creditId: String? = null
        var name: String? = null
        var originalName: String? = null
        var gender: PersonGender? = null
        var adult: Boolean? = null
        var profilePath: String? = null
        var knownForDepartment: String? = null
        var character: String? = null
        var job: String? = null

        var isGuest = false

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> personId = intAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "personId", "id", reader)
                1 -> creditId = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "creditId", "credit_id", reader)
                2 -> name = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "name", "name", reader)
                3 -> originalName = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "originalName", "original_name", reader)
                4 -> gender = nullablePersonGenderAdapter.fromJson(reader)
                5 -> adult = booleanAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "adult", "adult", reader)
                6 -> profilePath = nullableStringAdapter.fromJson(reader)
                7 -> knownForDepartment = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "knownForDepartment", "known_for_department", reader)
                8 -> character = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "character", "character", reader)
                9 -> {
                    character = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                        "character", "character_name", reader)
                    isGuest = true
                }
                10 -> job = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "job", "job", reader)
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return if (job != null) {
            TmdbCredit.Crew(
                personId ?: throw Util.missingProperty("personId", "id", reader),
                creditId ?: throw Util.missingProperty("creditId", "credit_id", reader),
                name ?: throw Util.missingProperty("name", "name", reader),
                originalName ?: throw Util.missingProperty("originalName", "original_name", reader),
                gender ?: throw Util.missingProperty("gender", "gender", reader),
                adult ?: throw Util.missingProperty("adult", "adult", reader),
                profilePath,
                knownForDepartment ?: throw Util.missingProperty("knownForDepartment", "known_for_department", reader),
                job
            )
        } else if (isGuest) {
            TmdbCredit.Guest(
                personId ?: throw Util.missingProperty("personId", "id", reader),
                creditId ?: throw Util.missingProperty("creditId", "credit_id", reader),
                name ?: throw Util.missingProperty("name", "name", reader),
                originalName ?: throw Util.missingProperty("originalName", "original_name", reader),
                gender,
                adult ?: throw Util.missingProperty("adult", "adult", reader),
                profilePath,
                knownForDepartment ?: throw Util.missingProperty("knownForDepartment", "known_for_department", reader),
                character ?: throw Util.missingProperty("character", "character", reader)
            )
        } else {
            TmdbCredit.Cast(
                personId ?: throw Util.missingProperty("personId", "id", reader),
                creditId ?: throw Util.missingProperty("creditId", "credit_id", reader),
                name ?: throw Util.missingProperty("name", "name", reader),
                originalName ?: throw Util.missingProperty("originalName", "original_name", reader),
                gender,
                adult ?: throw Util.missingProperty("adult", "adult", reader),
                profilePath,
                knownForDepartment ?: throw Util.missingProperty("knownForDepartment", "known_for_department", reader),
                character ?: throw Util.missingProperty("character", "character_name", reader)
            )
        }
    }

    override fun toJson(writer: JsonWriter, value: TmdbCredit?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        when (value) {
            is TmdbCredit.Cast -> {
                writer.beginObject()
                writer.endObject()
            }
            is TmdbCredit.Crew -> {
                writer.beginObject()
                writer.endObject()
            }
            is TmdbCredit.Guest -> {
                writer.beginObject()
                writer.endObject()
            }
        }

//        when (value) {
//            is TmdbTranslationData.Overview -> {
//                writer.beginObject()
//                writer.name("title")
//                stringAdapter.toJson(writer, value.title)
//                writer.name("overview")
//                stringAdapter.toJson(writer, value.overview)
//                writer.endObject()
//            }
//            is TmdbTranslationData.Biography -> {
//                writer.beginObject()
//                writer.name("biography")
//                stringAdapter.toJson(writer, value.biography)
//                writer.endObject()
//            }
//        }
    }
}