package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.MediaType
import de.vkay.api.tmdb.internals.getKeyValue
import de.vkay.api.tmdb.models.*
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@JsonQualifier
internal annotation class TaggedImages

/**
 * Reference: [Stackoverflow](https://stackoverflow.com/questions/53344033/moshi-parse-single-object-or-list-of-objects-kotlin)
 */
internal class TaggedImagesAdapter(
    moshi: Moshi
) : JsonAdapter<Any>() {

    private val imageJsonAdapter = moshi.adapter(TmdbImage::class.java)
    private val movieJsonAdapter = moshi.adapter(TmdbMovie.Slim::class.java)
    private val showJsonAdapter = moshi.adapter(TmdbShow.Slim::class.java)

    private val pageOptions: JsonReader.Options = JsonReader.Options.of(
        "page", "total_results", "total_pages", "results"
    )

    // movie, tv object
    private val mediaOptions: JsonReader.Options = JsonReader.Options.of("media")

    companion object {
        val INSTANCE = TaggedImagesFactory()
    }

    override fun fromJson(reader: JsonReader): Any {
        var page = 0
        var totalPages = 0
        var totalResults = 0
        val results = mutableListOf<Pair<TmdbImage, MediaTypeItem>>()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(pageOptions)) {
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
                0 -> {
                    page = reader.nextInt()
                }
                2 -> {
                    totalPages = reader.nextInt()
                }
                1 -> {
                    totalResults = reader.nextInt()
                }
                3 -> {
                    reader.beginArray()
                    while (reader.hasNext()) {
                        getPair(reader)?.let { results.add(it) }
                    }
                    reader.endArray()
                }
            }
        }

        reader.endObject()
        return TmdbPage(page, totalResults, totalPages, results)
    }

    private fun getPair(reader: JsonReader): Pair<TmdbImage, MediaTypeItem>? {
        val image = imageJsonAdapter.fromJson(reader.peekJson())
        var media: MediaTypeItem? = MediaTypeItem(MediaType.MOVIE)

        val mediaType = reader.getKeyValue(true, "media_type", String::class.java)

        reader.beginObject()
        while (reader.hasNext()) {
            when(reader.selectName(mediaOptions)) {
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
                0 -> {
                    media = when (mediaType) {
                        "tv" -> {
                            showJsonAdapter.fromJson(reader)
                        }
                        "movie" -> {
                            movieJsonAdapter.fromJson(reader)
                        }
                        else -> {
                            throw JsonDataException("Provided media (tagged image) is malformed, requires movie or tv")
                        }
                    }
                }
            }
        }
        reader.endObject()
        return Pair(image!!, media!!)
    }

    override fun toJson(writer: JsonWriter, value: Any?) =
        throw UnsupportedOperationException("TaggedImagesAdapter is only used to deserialize objects")

    class TaggedImagesFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Any>? {
            Types.nextAnnotations(annotations, TaggedImages::class.java) ?: return null
            return TaggedImagesAdapter(moshi)
        }
    }
}