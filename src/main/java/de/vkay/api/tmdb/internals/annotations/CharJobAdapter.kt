package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.models.*
import java.lang.reflect.Type
import kotlin.streams.toList

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class CharJobAdapter(
    moshi: Moshi,
    fieldName: String,
    private val mediaType: MediaType
) : JsonAdapter<Any>() {

    private val movieJsonAdapter = moshi.adapter(TmdbMovie.Slim::class.java)
    private val showJsonAdapter = moshi.adapter(TmdbShow.Slim::class.java)
    private val mediaItemJsonAdapter = moshi.adapter(MediaTypeItem::class.java)

    private val options: JsonReader.Options = JsonReader.Options.of(fieldName)
    private val creditOptions: JsonReader.Options = JsonReader.Options.of(
        "credit_id",
        "character",
        "job",
        "episode_count"
    )

    companion object {
        val INSTANCE = RoleJobFactory()
    }

    override fun fromJson(reader: JsonReader): Any {
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
                    var tmpCreditId = "INVALID"
                    var tmpJob: String? = null
                    var tmpCharacter: String? = null
                    var tmpEpCount: Int? = null

                    reader.beginArray()
                    // Iterate over all items
                    while (reader.hasNext()) {
                        val tmpReader = reader.peekJson()
                        val cr = getCredit(tmpReader)!!

                        when (mediaType) {
                            MediaType.TV -> {
                                showJsonAdapter.fromJson(reader)?.let {
                                    result.add(Pair(it, cr))
                                }
                            }
                            MediaType.MOVIE -> {
                                movieJsonAdapter.fromJson(reader)?.let {
                                    result.add(Pair(it, cr))
                                }
                            }
                            else -> {
                                mediaItemJsonAdapter.fromJson(reader)?.let {
                                    result.add(Pair(it, cr))
                                }
                            }
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
        throw UnsupportedOperationException("ResultsListAdapter is only used to deserialize objects")

    class RoleJobFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Any>? {
            Types.nextAnnotations(annotations, CharJob::class.java) ?: return null

            val rawType = Types.getRawType(type)

            if (!TmdbError.isAnyError(rawType)) {
                if (Types.getRawType(type) != List::class.java )
                    throw IllegalArgumentException("Only lists may be annotated with @ResultsList. Found: $type")
            }

            val resListAnno = annotations.stream().filter { it is CharJob }.toList().firstOrNull()
                ?: throw IllegalArgumentException("List has no valid fieldName: $type")

            val fieldName = (resListAnno as CharJob).fieldName
            val mediaType = resListAnno.mediaType

            return CharJobAdapter(moshi, fieldName, mediaType)
        }
    }
}