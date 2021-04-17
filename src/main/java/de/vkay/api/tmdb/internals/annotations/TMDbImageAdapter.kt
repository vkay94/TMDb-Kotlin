package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.models.TmdbImage
import java.lang.reflect.Type

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class TMDbImageAdapter: JsonAdapter<TmdbImage?>() {
    private val stringAdapter: JsonAdapter<String?> = Moshi.Builder().build().adapter(String::class.java)

    companion object {
        val INSTANCE = TMDbImageFactory()
    }

    override fun fromJson(reader: JsonReader): TmdbImage? = stringAdapter.fromJson(reader)?.let { TmdbImage(it) }

    override fun toJson(writer: JsonWriter, value: TmdbImage?) =
        throw UnsupportedOperationException("TMDBImageAdapter is only used to deserialize objects")

    class TMDbImageFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<TmdbImage?>? {
            Types.nextAnnotations(annotations, TMDbImage::class.java) ?: return null

            if (Types.getRawType(type) != TmdbImage::class.java )
                throw IllegalArgumentException(
                    "Only TmdbImage may be annotated with @TMDbImage. Found: ${type.typeName}"
                )

            return TMDbImageAdapter()
        }
    }
}