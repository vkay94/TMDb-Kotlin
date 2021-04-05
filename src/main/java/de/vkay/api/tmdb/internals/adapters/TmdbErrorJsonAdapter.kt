package de.vkay.api.tmdb.internals.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import de.vkay.api.tmdb.models.TmdbError
import de.vkay.api.tmdb.models.TmdbError_DefaultErrorJsonAdapter
import de.vkay.api.tmdb.models.TmdbError_PostErrorJsonAdapter

internal class TmdbErrorJsonAdapter : JsonAdapter<TmdbError>() {
    private val moshi = Moshi.Builder().build()

    private val defaultErrorJsonAdapter = TmdbError_DefaultErrorJsonAdapter(moshi)
    private val postErrorJsonAdapter = TmdbError_PostErrorJsonAdapter(moshi)

    private val errorsOptions: JsonReader.Options = JsonReader.Options.of(
        "errors"
    )

    override fun fromJson(reader: JsonReader): TmdbError {
        val peek = reader.peekJson()
        peek.beginObject()

        val hasErrors = peek.selectName(errorsOptions) != -1
        return if (hasErrors) {
            postErrorJsonAdapter.fromJson(reader)
        } else {
            defaultErrorJsonAdapter.fromJson(reader)
        }
    }

    override fun toJson(writer: JsonWriter, value: TmdbError?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
    }
}