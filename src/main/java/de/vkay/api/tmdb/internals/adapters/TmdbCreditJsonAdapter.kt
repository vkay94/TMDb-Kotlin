package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.PersonGender
import de.vkay.api.tmdb.internals.EnumValueJsonAdapter
import de.vkay.api.tmdb.models.TmdbCredit
import java.util.*

internal class TmdbCreditCastJsonAdapter : JsonAdapter<TmdbCredit.Cast>() {

    private val moshi = Moshi.Builder()
        .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
            .withUnknownFallback(PersonGender.OTHER))
        .add(TmdbCredit.RoleJob::class.java, TmdbCreditRoleJobJsonAdapter())
        .build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "id",               // 0
        "name",             // 1
        "original_name",    // 2
        "gender",           // 3
        "adult",            // 4
        "profile_path",     // 5
        "known_for_department", // 6
        "credit_id",        // 7
        "character",        // 8
        "roles",            // 9
        "total_episode_count", // 10
        "order"            // 11
    )

    private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java)
    private val nullableIntAdapter: JsonAdapter<Int?> = moshi.adapter(Int::class.java)
    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java)
    private val booleanAdapter: JsonAdapter<Boolean> = moshi.adapter(Boolean::class.java)

    private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java)
    private val nullablePersonGenderAdapter: JsonAdapter<PersonGender?> = moshi.adapter(PersonGender::class.java)

    private val listOfTmdbRoleJobAdapter: JsonAdapter<List<TmdbCredit.RoleJob>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, TmdbCredit.RoleJob::class.java))

    override fun fromJson(reader: JsonReader): TmdbCredit.Cast {
        var personId: Int? = null
        var name: String? = null
        var originalName: String? = null
        var gender: PersonGender? = null
        var adult: Boolean? = null
        var profilePath: String? = null
        var knownForDepartment: String? = null
        var creditId: String? = null
        var character: String? = null

        var totalEpisodeCount: Int? = null
        var order: Int? = null
        var roles: List<TmdbCredit.RoleJob>? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                /*
                    "id",               // 0
                    "name",             // 1
                    "original_name",    // 2
                    "gender",           // 3
                    "adult",            // 4
                    "profile_path",     // 5
                    "known_for_department", // 6
                    "credit_id",        // 7
                    "character",        // 8
                    "roles",            // 9
                    "total_episode_count", // 10
                    "order"            // 11
                 */
                0 -> personId = intAdapter.fromJson(reader)
                1 -> name = stringAdapter.fromJson(reader)
                2 -> originalName = stringAdapter.fromJson(reader)
                3 -> gender = nullablePersonGenderAdapter.fromJson(reader)
                4 -> adult = booleanAdapter.fromJson(reader)
                5 -> profilePath = nullableStringAdapter.fromJson(reader)
                6 -> knownForDepartment = stringAdapter.fromJson(reader)
                7 -> creditId = nullableStringAdapter.fromJson(reader)
                8 -> character = nullableStringAdapter.fromJson(reader)
                9 -> roles = listOfTmdbRoleJobAdapter.fromJson(reader)
                10 -> totalEpisodeCount = nullableIntAdapter.fromJson(reader)
                11 -> order = intAdapter.fromJson(reader)
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        if (roles == null) {
            roles = Collections.singletonList(
                TmdbCredit.RoleJob(
                creditId ?: "UNKNWON", character ?: "UNKNOWN", null
            ))
        }

        return TmdbCredit.Cast(
            personId = personId!!,
            name = name!!,
            originalName = originalName!!,
            gender = gender,
            adult = adult!!,
            _profilePath = profilePath,
            knownForDepartment = knownForDepartment!!,
            roles = roles ?: emptyList(),
            totalEpisodeCount = totalEpisodeCount,
            order = order!!
        )
    }

    override fun toJson(writer: JsonWriter, value: TmdbCredit.Cast?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}

internal class TmdbCreditCrewJsonAdapter : JsonAdapter<TmdbCredit.Crew>() {

    private val moshi = Moshi.Builder()
        .add(PersonGender::class.java, EnumValueJsonAdapter.create(PersonGender::class.java)
            .withUnknownFallback(PersonGender.OTHER))
        .add(TmdbCredit.RoleJob::class.java, TmdbCreditRoleJobJsonAdapter())
        .build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "id",               // 0
        "name",             // 1
        "original_name",    // 2
        "gender",           // 3
        "adult",            // 4
        "profile_path",     // 5
        "known_for_department", // 6
        "credit_id",        // 7
        "department",       // 8
        "jobs",            // 9
        "total_episode_count", // 10
        "job"               // 11
    )

    private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java)
    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java)
    private val booleanAdapter: JsonAdapter<Boolean> = moshi.adapter(Boolean::class.java)

    private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(String::class.java)
    private val nullableIntAdapter: JsonAdapter<Int?> = moshi.adapter(Int::class.java)
    private val nullablePersonGenderAdapter: JsonAdapter<PersonGender?> = moshi.adapter(PersonGender::class.java)

    private val listOfTmdbRoleJobAdapter: JsonAdapter<List<TmdbCredit.RoleJob>> =
        moshi.adapter(Types.newParameterizedType(List::class.java, TmdbCredit.RoleJob::class.java))

    override fun fromJson(reader: JsonReader): TmdbCredit.Crew {
        var personId: Int? = null
        var name: String? = null
        var originalName: String? = null
        var gender: PersonGender? = null
        var adult: Boolean? = null
        var profilePath: String? = null
        var knownForDepartment: String? = null
        var creditId: String? = null
        var department: String? = null

        var totalEpisodeCount: Int? = null
        var job: String? = null
        var jobs: List<TmdbCredit.RoleJob>? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                /*
                    "id",               // 0
                    "name",             // 1
                    "original_name",    // 2
                    "gender",           // 3
                    "adult",            // 4
                    "profile_path",     // 5
                    "known_for_department", // 6
                    "credit_id",        // 7
                    "department",        // 8
                    "jobs",            // 9
                    "total_episode_count", // 10
                    "job"            // 11
                 */
                0 -> personId = intAdapter.fromJson(reader)
                1 -> name = stringAdapter.fromJson(reader)
                2 -> originalName = stringAdapter.fromJson(reader)
                3 -> gender = nullablePersonGenderAdapter.fromJson(reader)
                4 -> adult = booleanAdapter.fromJson(reader)
                5 -> profilePath = nullableStringAdapter.fromJson(reader)
                6 -> knownForDepartment = stringAdapter.fromJson(reader)
                7 -> creditId = nullableStringAdapter.fromJson(reader)
                8 -> department = nullableStringAdapter.fromJson(reader)
                9 -> jobs = listOfTmdbRoleJobAdapter.fromJson(reader)
                10 -> totalEpisodeCount = nullableIntAdapter.fromJson(reader)
                11 -> job = stringAdapter.fromJson(reader)
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        if (jobs == null) {
            jobs = Collections.singletonList(
                TmdbCredit.RoleJob(
                    creditId ?: "UNKNWON", job ?: "UNKNOWN", null
                ))
        }

        return TmdbCredit.Crew(
            personId = personId!!,
            name = name!!,
            originalName = originalName!!,
            gender = gender,
            adult = adult!!,
            _profilePath = profilePath,
            knownForDepartment = knownForDepartment!!,
            jobs = jobs ?: emptyList(),
            totalEpisodeCount = totalEpisodeCount,
            department = department!!
        )
    }

    override fun toJson(writer: JsonWriter, value: TmdbCredit.Crew?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}

internal class TmdbCreditRoleJobJsonAdapter : JsonAdapter<TmdbCredit.RoleJob>() {

    private val moshi = Moshi.Builder().build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "credit_id",        // 0
        "character",        // 1
        "job",              // 2
        "episode_count",    // 3
    )

    private val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java)
    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java)

    override fun fromJson(reader: JsonReader): TmdbCredit.RoleJob {
        var creditId: String? = null
        var character: String? = null
        var job: String? = null
        var episodeCount: Int? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> creditId = stringAdapter.fromJson(reader)
                1 -> character = stringAdapter.fromJson(reader)
                2 -> job = stringAdapter.fromJson(reader)
                3 -> episodeCount = intAdapter.fromJson(reader)
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return TmdbCredit.RoleJob(
            creditId = creditId!!,
            jobCharacter = job ?: character!!,
            episodeCount = episodeCount ?: 0
        )
    }

    override fun toJson(writer: JsonWriter, value: TmdbCredit.RoleJob?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}