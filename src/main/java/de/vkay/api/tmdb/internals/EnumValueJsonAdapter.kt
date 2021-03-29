package de.vkay.api.tmdb.internals

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException

/**
 * Source: [GitHub](https://github.com/square/moshi/issues/263#issuecomment-480599129)
 */
internal class EnumValueJsonAdapter<T : IValueEnum>
private constructor(
    private val enumType: Class<T>, private val fallbackValue: T?,
    private val useFallbackValue: Boolean
) : JsonAdapter<T>() {
    private val constants: Array<T> = enumType.enumConstants as Array<T>
    private val enumValues: HashMap<Int, T> = constants.associateByTo(hashMapOf()) { it.value }

    /**
     * Create a new adapter for this enum with a fallback value to use when the JSON value does not
     * match any of the enum's constants. Note that this value will not be used when the JSON value is
     * null, absent, or not a string. Also, the string values are case-sensitive, and this fallback
     * value will be used even on case mismatches.
     */
    fun withUnknownFallback(fallbackValue: T?): EnumValueJsonAdapter<T> =
        EnumValueJsonAdapter(enumType, fallbackValue, true)

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): T? {
        val value = reader.nextInt()
        if (enumValues.containsKey(value))
            return enumValues[value]

        val path = reader.path
        if (!useFallbackValue) {
            throw JsonDataException("Unknown value of enum ${enumType.name} ($value) at path $path")
        }
        return fallbackValue
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: T?) {
        if (value == null) {
            throw NullPointerException("value was null! Wrap in .nullSafe() to write nullable values.")
        }
        writer.value(value.value)
    }

    override fun toString() = "EnumJsonAdapter(" + enumType.name + ")"

    companion object {
        fun <T : IValueEnum> create(
            enumType: Class<T>,
            unknownFallback: T? = null
        ): EnumValueJsonAdapter<T> {
            val useFallbackValue = (unknownFallback != null)
            return EnumValueJsonAdapter(enumType, unknownFallback, useFallbackValue)
        }
    }
}