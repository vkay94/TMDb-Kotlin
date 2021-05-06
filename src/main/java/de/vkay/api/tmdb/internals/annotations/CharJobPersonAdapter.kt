package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.models.MediaTypeItem
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbPerson
import java.lang.reflect.Type
import kotlin.streams.toList

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class CharJobPersonAdapter(
    moshi: Moshi,
    fieldName: String,
) : JsonAdapter<Any>() {

    private val personJsonAdapter = moshi.adapter(TmdbPerson.Slim::class.java)
    private val options: JsonReader.Options = JsonReader.Options.of(fieldName)

    companion object {
        val INSTANCE = CharJobPersonFactory()
    }

    override fun fromJson(reader: JsonReader): Any {
        // Any = CrewJon, CastRole
        val result = mutableListOf<Pair<MediaTypeItem, Any>>()
        reader.beginObject()

        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                else -> {
                    reader.beginArray()
                    // Iterate over all items
                    while (reader.hasNext()) {
                        val rc = reader.peekJson()
                        val jobChar = getCredit(rc)!!

                        personJsonAdapter.fromJson(reader)?.let {
                            result.add(Pair(it, jobChar))
                        }
                    }
                    reader.endArray()
                }
            }
        }

        reader.endObject()
        return result
    }

    private fun getCredit(reader: JsonReader): Any? {
        val options: JsonReader.Options = JsonReader.Options.of(
            "credit_id",        // 0
            "character",        // 1
            "job",              // 2
            "episode_count",    // 3
            "order",            // 4
            "department",       // 5
            "roles",            // 6    => credit_id, character, episode_count
            "jobs"              // 7
        )

        var creditId = "INVALID"
        var character: String? = null
        var job: String? = null
        var episodeCount: Int? = null
        var order = -1
        var department = "NOT_FOUND"

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
                0 -> creditId = reader.nextString()
                1 -> character = reader.nextString()
                2 -> job = reader.nextString()
                3 -> episodeCount = reader.nextInt()
                4 -> order = reader.nextInt()
                5 -> department = reader.nextString()
                6 -> {
                    // Roles
                    reader.beginArray()
                    while (reader.hasNext()) {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.selectName(options)) {
                                -1 -> {
                                    reader.skipName()
                                    reader.skipValue()
                                }
                                0 -> creditId = reader.nextString()
                                1 -> character = reader.nextString()
                                3 -> episodeCount = reader.nextInt()
                            }
                        }
                        reader.endObject()
                    }
                    reader.endArray()
                }
                7 -> {
                    // Jobs
                    reader.beginArray()
                    while (reader.hasNext()) {
                        reader.beginObject()
                        when (reader.selectName(options)) {
                            -1 -> {
                                reader.skipName()
                                reader.skipValue()
                            }
                            0 -> creditId = reader.nextString()
                            2 -> job = reader.nextString()
                            3 -> episodeCount = reader.nextInt()
                        }
                        reader.endObject()
                    }
                    reader.endArray()
                }
            }
        }
        reader.endObject()

        return when {
            character != null -> TmdbPerson.CastRole(creditId, character, order, episodeCount)
            job != null -> TmdbPerson.CrewJob(creditId, job, department, episodeCount)
            else -> null
        }
    }

    override fun toJson(writer: JsonWriter, value: Any?) =
        throw UnsupportedOperationException("CharJobPersonAdapter is only used to deserialize objects")

    class CharJobPersonFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Any>? {
            Types.nextAnnotations(annotations, CharJobPerson::class.java) ?: return null

            val rawType = Types.getRawType(type)

            if (!TmdbError.isAnyError(rawType)) {
                if (Types.getRawType(type) != List::class.java )
                    throw IllegalArgumentException("Only lists may be annotated with @CharJobPerson. Found: $type")
            }

            val resListAnno = annotations.stream().filter { it is CharJobPerson }.toList().firstOrNull()
                ?: throw IllegalArgumentException("List has no valid fieldName: $type")

            val fieldName = (resListAnno as CharJobPerson).fieldName

            return CharJobPersonAdapter(moshi, fieldName)
        }
    }
}