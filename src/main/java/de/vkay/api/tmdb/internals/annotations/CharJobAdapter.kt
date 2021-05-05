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
        val result = mutableListOf<Pair<MediaTypeItem, TmdbPerson.RoleJob>>()
        reader.beginObject()

        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                else -> {
                    var tmpCreditId = "#"
                    var tmpJob: String? = null
                    var tmpCharacter: String? = null
                    var tmpEpCount: Int? = null

                    reader.beginArray()
                    // Iterate over all items
                    while (reader.hasNext()) {
                        val rc = reader.peekJson()
                        rc.beginObject()
                        // Go through all json keys
                        while (rc.hasNext()) {
                            when (rc.selectName(creditOptions)) {
                                -1 -> {
                                    rc.skipName()
                                    rc.skipValue()
                                }
                                0 -> tmpCreditId = rc.nextString()
                                1 -> tmpCharacter = rc.nextString()
                                2 -> tmpJob = rc.nextString()
                                3 -> tmpEpCount = rc.nextInt()
                            }
                        }
                        rc.endObject()

                        when (mediaType) {
                            MediaType.TV -> {
                                showJsonAdapter.fromJson(reader)?.let {
                                    val jobChar = tmpCharacter ?: tmpJob ?: "NOT_FOUND"
                                    val job = TmdbPerson.RoleJob(tmpCreditId, jobChar, tmpEpCount)
                                    result.add(Pair(it, job))
                                }
                            }
                            MediaType.MOVIE -> {
                                movieJsonAdapter.fromJson(reader)?.let {
                                    val jobChar = tmpCharacter ?: tmpJob ?: "NOT_FOUND"
                                    val job = TmdbPerson.RoleJob(tmpCreditId, jobChar, tmpEpCount)
                                    result.add(Pair(it, job))
                                }
                            }
                            else -> {
                                mediaItemJsonAdapter.fromJson(reader)?.let {
                                    val jobChar = tmpCharacter ?: tmpJob ?: "NOT_FOUND"
                                    val job = TmdbPerson.RoleJob(tmpCreditId, jobChar, tmpEpCount)
                                    result.add(Pair(it, job))
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