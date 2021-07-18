package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import de.vkay.api.tmdb.models.TmdbTranslation

internal class TmdbTranslationDataJsonAdapter : JsonAdapter<TmdbTranslation.Data>() {
    private val moshi = Moshi.Builder().build()

    private val options: JsonReader.Options = JsonReader.Options.of(
        "name", "overview", "title", "biography"
    )

    private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
        "name")

    override fun fromJson(reader: JsonReader): TmdbTranslation.Data {
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
            TmdbTranslation.Data.Overview(
                title ?: throw Util.missingProperty("title", "title/name", reader),
                overview ?: throw Util.missingProperty("overview", "overview", reader)
            )
        } else {
            TmdbTranslation.Data.Biography(biography)
        }
    }

    override fun toJson(writer: JsonWriter, value: TmdbTranslation.Data?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}