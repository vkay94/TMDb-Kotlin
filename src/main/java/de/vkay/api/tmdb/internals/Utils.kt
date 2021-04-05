package de.vkay.api.tmdb.internals

import com.squareup.moshi.JsonReader

internal fun JsonReader.hasNotKeys(isObject: Boolean, vararg keys: String): Boolean {
    val options: JsonReader.Options = JsonReader.Options.of(*keys)
    val peek: JsonReader = this.peekJson()

    if (isObject) peek.beginObject()

    while (peek.hasNext()) {
        if (peek.selectName(options) == -1)
            return true // one of the keys is not found
    }
    return true
}