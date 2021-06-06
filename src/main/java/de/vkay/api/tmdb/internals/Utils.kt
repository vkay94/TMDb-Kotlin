package de.vkay.api.tmdb.internals

import com.squareup.moshi.JsonReader

internal fun JsonReader.hasKeys(isObject: Boolean, vararg keys: String): Boolean {
    val options: JsonReader.Options = JsonReader.Options.of(*keys)
    val peek: JsonReader = this.peekJson()

    if (isObject) peek.beginObject()

    while (peek.hasNext()) {
        if (peek.selectName(options) == -1)
            return false // one of the keys is not found
    }
    // all keys are found
    return true
}

internal fun JsonReader.getKeyValue(isObject: Boolean, key: String, clazz: Class<*>): Any? {
    val options: JsonReader.Options = JsonReader.Options.of(key)
    val peek: JsonReader = this.peekJson()

    if (isObject) peek.beginObject()

    while (peek.hasNext()) {
        if (peek.selectName(options) == 0) {
            return when (clazz) {
                String::class.java -> peek.nextString()
                Int::class.java -> peek.nextInt()
                Boolean::class.java -> peek.nextBoolean()
                Double::class.java -> peek.nextDouble()
                Long::class.java -> peek.nextLong()
                else -> null
            }
        } else {
            peek.skipName()
            peek.skipValue()
        }
    }
    return null
}