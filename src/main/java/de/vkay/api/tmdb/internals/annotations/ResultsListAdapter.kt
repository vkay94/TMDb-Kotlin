package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import java.lang.reflect.Type

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
class ResultsListAdapter(
    private val delegateAdapter: JsonAdapter<List<Any>>,
) : JsonAdapter<Any>() {

    private val options: JsonReader.Options = JsonReader.Options.of(
        "results", "translations", "genres", "logos", "profiles"
    )

    companion object {
        val INSTANCE = ResultsListFactory()
    }

    override fun fromJson(reader: JsonReader): Any? {
        var results: List<Any>? = null
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                else -> results = delegateAdapter.fromJson(reader)
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

            if (Types.getRawType(type) != List::class.java)
                throw IllegalArgumentException("Only lists may be annotated with @ResultsList. Found: $type")

            val delegateAdapter: JsonAdapter<List<Any>> = moshi.adapter(type, delegateAnnotations)
            return ResultsListAdapter(delegateAdapter)
        }
    }
}