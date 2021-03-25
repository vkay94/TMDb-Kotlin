package playground

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

fun main() {

    val map: MutableMap<String, Animal> = mutableMapOf()
    val dog = Animal("Dog")
    val cat = Animal("Cat")

    map["DE"] = dog
    map["EN"] = cat

    val mapType = Types.newParameterizedType(Map::class.java, String::class.java, Animal::class.java)

    val internalMoshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<Map<String, Animal>> = internalMoshi.adapter(mapType)

    val moshi = Moshi.Builder().add(mapType, jsonAdapter).build()

    val jsonString = moshi.adapter<Map<String, Animal>>(mapType).toJson(map)

    println(jsonString)
}

@JsonClass(generateAdapter = true)
data class Animal(val name: String)