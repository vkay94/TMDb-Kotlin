package de.vkay.tmdb.internals.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import de.vkay.tmdb.models.TmdbTranslationData

internal class TmdbTranslationDataJsonAdapter : JsonAdapter<TmdbTranslationData>() {
    private val moshi = Moshi.Builder().build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "name", "overview", "title", "biography"
    )

    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
        "name")

    override fun fromJson(reader: JsonReader): TmdbTranslationData? {
        var overview: String? = null
        var title: String? = null
        var biography: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0, 2 -> title = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "title", "title/name", reader)
                1 -> overview = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "overview", "overview", reader)
                3 -> biography = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "biography", "biography", reader)
                -1 -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        return if (biography == null) {
            TmdbTranslationData.Overview(
                title ?: throw Util.missingProperty("title", "title/name", reader),
                overview ?: throw Util.missingProperty("overview", "overview", reader)
            )
        } else {
            TmdbTranslationData.Biography(biography)
        }
    }

    override fun toJson(writer: JsonWriter, value: TmdbTranslationData?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }

        when (value) {
            is TmdbTranslationData.Overview -> {
                writer.beginObject()
                writer.name("title")
                stringAdapter.toJson(writer, value.title)
                writer.name("overview")
                stringAdapter.toJson(writer, value.overview)
                writer.endObject()
            }
            is TmdbTranslationData.Biography -> {
                writer.beginObject()
                writer.name("biography")
                stringAdapter.toJson(writer, value.biography)
                writer.endObject()
            }
        }
    }
}