package de.vkay.api.tmdb.internals.annotations

import com.squareup.moshi.*
import de.vkay.api.tmdb.enumerations.MediaType
import java.lang.reflect.Type

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@JsonQualifier
internal annotation class ListComments

internal class ListCommentsAdapter: JsonAdapter<List<Triple<MediaType, Int, String?>>>() {

    companion object {
        val INSTANCE = ListCommentsFactory()
    }

    override fun fromJson(reader: JsonReader): List<Triple<MediaType, Int, String?>> {
        val result = mutableListOf<Triple<MediaType, Int, String?>>()

        reader.beginObject()
        while (reader.hasNext()) {
            val typeId = reader.nextName().split(":")
            val peek = reader.peekJson()
            reader.skipValue()

            result.add(Triple(
                if (typeId[0] == "tv") MediaType.TV else MediaType.MOVIE,
                typeId[1].toInt(),
                try { peek.nextString() } catch (e: JsonDataException) { null }
            ))
        }
        reader.endObject()

        return result
    }

    override fun toJson(writer: JsonWriter, value: List<Triple<MediaType, Int, String?>>?) =
        throw UnsupportedOperationException("ListCommentsAdapter is only used to deserialize objects")

    class ListCommentsFactory : Factory {
        override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<List<Triple<MediaType, Int, String?>>>? {
            Types.nextAnnotations(annotations, ListComments::class.java) ?: return null
            return ListCommentsAdapter()
        }
    }
}