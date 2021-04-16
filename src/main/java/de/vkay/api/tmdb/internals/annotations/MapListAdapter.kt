package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.ReleaseDate
import de.vkay.api.tmdb.internals.EnumValueJsonAdapter
import de.vkay.api.tmdb.internals.adapters.TmdbDateJsonAdapter
import de.vkay.api.tmdb.models.TmdbReleaseDate
import java.lang.reflect.Type

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class MapListAdapter: JsonAdapter<Map<String, List<TmdbReleaseDate>>>() {

    private val options: JsonReader.Options = JsonReader.Options.of("results")
    private val listResultsType = Types.newParameterizedType(List::class.java, ReleaseDatesMapHelper::class.java)
    private val listResultsAdapter = Moshi.Builder()
        .add(TmdbDateJsonAdapter())
        .add(
            ReleaseDate::class.java, EnumValueJsonAdapter.create(ReleaseDate::class.java)
            .withUnknownFallback(ReleaseDate.UNKNOWN))
        .build().adapter<List<ReleaseDatesMapHelper>>(listResultsType)

    companion object {
        val INSTANCE = MapListFactory()
    }

    override fun fromJson(reader: JsonReader): Map<String, List<TmdbReleaseDate>> {
        val results: HashMap<String, List<TmdbReleaseDate>> = HashMap()
        var tmpResults: List<ReleaseDatesMapHelper> = emptyList()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> {
                    tmpResults = listResultsAdapter.fromJson(reader)!!
                }
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                else -> {
                    println("-")
                }
            }
        }
        reader.endObject()
        tmpResults.forEach {
            results[it.iso_3166_1] = it.release_dates
        }
        return results
    }

    override fun toJson(writer: JsonWriter, value: Map<String, List<TmdbReleaseDate>>?) =
        throw UnsupportedOperationException("MapListAdapter is only used to deserialize objects")

    class MapListFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Map<String, List<TmdbReleaseDate>>>? {
            val delegateAnnotations = Types.nextAnnotations(annotations, MapList::class.java) ?: return null

            return MapListAdapter()
        }
    }
}

@JsonClass(generateAdapter = true)
internal data class ReleaseDatesMapHelper(
    val iso_3166_1: String,
    val release_dates: List<TmdbReleaseDate>
)