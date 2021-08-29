package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
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
internal class MapListAdapter(val moshi: Moshi, val fieldName: String): JsonAdapter<Map<String, List<Any>>>() {

    private val listResultsType = Types.newParameterizedType(List::class.java, ReleaseDatesMapHelper::class.java)
    private val listStringType = Types.newParameterizedType(List::class.java, String::class.java)

    private val listResultsAdapter = moshi.adapter<List<ReleaseDatesMapHelper>>(listResultsType)
    private val listStringAdapter = moshi.adapter<List<String>>(listStringType)

    companion object {
        val INSTANCE = MapListFactory()
    }

    override fun fromJson(reader: JsonReader): Map<String, List<Any>> {
        // Movie: Release dates
        if (fieldName == "release_dates") {
            val options: JsonReader.Options = JsonReader.Options.of("results")

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

        // Configuration: Jobs
        if (fieldName == "jobs") {
            val options: JsonReader.Options = JsonReader.Options.of("department", "jobs")
            val results: HashMap<String, List<String>> = HashMap()

            var tmpDepartment = ""
            var tmpJobs: List<String> = emptyList()

            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.selectName(options)) {
                        0 -> {
                            tmpDepartment = reader.nextString()
                        }
                        1 -> {
                            tmpJobs = listStringAdapter.fromJson(reader) ?: emptyList()
                        }
                        else -> {
                            // Unknown name, skip it.
                            reader.skipName()
                            reader.skipValue()
                        }
                    }
                }
                reader.endObject()
                results[tmpDepartment] = tmpJobs
                tmpDepartment = ""
                tmpJobs = emptyList()
            }
            reader.endArray()

            return results
        }

        // Configuration: Timezones
        if (fieldName == "timezones") {
            val options: JsonReader.Options = JsonReader.Options.of("iso_3166_1", "zones")
            val results: HashMap<String, List<String>> = HashMap()

            var tmpIso = ""
            var tmpZones: List<String> = emptyList()

            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                while (reader.hasNext()) {
                    when (reader.selectName(options)) {
                        0 -> {
                            tmpIso = reader.nextString()
                        }
                        1 -> {
                            tmpZones = listStringAdapter.fromJson(reader) ?: emptyList()
                        }
                        else -> {
                            // Unknown name, skip it.
                            reader.skipName()
                            reader.skipValue()
                        }
                    }
                }
                reader.endObject()
                results[tmpIso] = tmpZones
                tmpIso = ""
                tmpZones = emptyList()
            }
            reader.endArray()

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

            return MapListAdapter(moshi, fieldName)
        }
    }
}

@JsonClass(generateAdapter = true)
internal data class ReleaseDatesMapHelper(
    val iso_3166_1: String,
    val release_dates: List<TmdbReleaseDate>
)