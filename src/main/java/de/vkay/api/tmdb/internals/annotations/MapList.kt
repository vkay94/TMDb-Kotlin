package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.ReleaseType
import de.vkay.api.tmdb.internals.EnumValueJsonAdapter
import de.vkay.api.tmdb.models.TmdbDate
import de.vkay.api.tmdb.models.TmdbReleaseDate
import java.lang.reflect.Type
import kotlin.streams.toList

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@JsonQualifier
internal annotation class MapList(val fieldName: String = "results")

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class MapListAdapter(val fieldName: String): JsonAdapter<Map<String, List<Any>>>() {

    private val options: JsonReader.Options = JsonReader.Options.of("results")
    private val optionsCertification: JsonReader.Options = JsonReader.Options.of("certifications")

    private val listResultsType = Types.newParameterizedType(List::class.java, ReleaseDatesMapHelper::class.java)
    private val listResultsAdapter = Moshi.Builder()
        .add(TmdbDate.ADAPTER)
        .add(
            ReleaseType::class.java, EnumValueJsonAdapter.create(ReleaseType::class.java)
                .withUnknownFallback(ReleaseType.UNKNOWN))
        .build().adapter<List<ReleaseDatesMapHelper>>(listResultsType)

    companion object {
        val INSTANCE = MapListFactory()
    }

    override fun fromJson(reader: JsonReader): Map<String, List<Any>> {
        if (fieldName == "results") {
            val results: HashMap<String, List<TmdbReleaseDate>> = HashMap()
            var tmpResults: List<ReleaseDatesMapHelper> = emptyList()

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.selectName(options)) {
                    0 -> {
                        tmpResults = listResultsAdapter.fromJson(reader)!!
                    }
                    else -> {
                        // Unknown name, skip it.
                        reader.skipName()
                        reader.skipValue()
                    }
                }
            }
            reader.endObject()
            tmpResults.forEach {
                results[it.iso_3166_1] = it.release_dates
            }
            return results
        }
        return emptyMap()
    }

    override fun toJson(writer: JsonWriter, value: Map<String, List<Any>>?) =
        throw UnsupportedOperationException("MapListAdapter is only used to deserialize objects")

    class MapListFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Map<String, List<Any>>>? {
            val delegateAnnotations = Types.nextAnnotations(annotations, MapList::class.java) ?: return null

            val resListAnnotation = annotations.stream().filter { it is MapList }.toList().firstOrNull()
                ?: throw IllegalArgumentException("List has no valid fieldName: $type")

            val fieldName = (resListAnnotation as MapList).fieldName

            return MapListAdapter(fieldName)
        }
    }
}

@JsonClass(generateAdapter = true)
internal data class ReleaseDatesMapHelper(
    val iso_3166_1: String,
    val release_dates: List<TmdbReleaseDate>
)