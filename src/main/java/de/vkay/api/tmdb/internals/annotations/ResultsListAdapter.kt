package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.models.TmdbError
import java.lang.reflect.Type
import kotlin.streams.toList

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class ResultsListAdapter(
    private val delegateAdapter: JsonAdapter<List<Any>>,
    fieldName: String
) : JsonAdapter<Any>() {

    private val options: JsonReader.Options = JsonReader.Options.of(fieldName)

    companion object {
        val INSTANCE = ResultsListFactory()
    }

    override fun fromJson(reader: JsonReader): Any {
        var results: List<Any> = emptyList()
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                else -> results = delegateAdapter.fromJson(reader) ?: emptyList()
            }
        }
        reader.endObject()
        return results
    }

    override fun toJson(writer: JsonWriter, value: Any?) =
        throw UnsupportedOperationException("ResultsListAdapter is only used to deserialize objects")

    class ResultsListFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Any>? {
            val delegateAnnotations = Types.nextAnnotations(annotations, ResultsList::class.java) ?: return null

            val rawType = Types.getRawType(type)

            if (!TmdbError.isAnyError(rawType)) {
                if (Types.getRawType(type) != List::class.java )
                    throw IllegalArgumentException("Only lists may be annotated with @ResultsList. Found: $type")
            }

            val resListAnno = annotations.stream().filter { it is ResultsList }.toList().firstOrNull()
                ?: throw IllegalArgumentException("List has no valid fieldName: $type")

            val fieldName = (resListAnno as ResultsList).fieldName

            val delegateAdapter: JsonAdapter<List<Any>> = moshi.adapter(type, delegateAnnotations)
            return ResultsListAdapter(delegateAdapter, fieldName)
        }
    }
}