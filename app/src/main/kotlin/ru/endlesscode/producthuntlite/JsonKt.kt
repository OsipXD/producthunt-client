@file:JvmName("JsonKt")
package ru.endlesscode.producthuntlite

import com.google.gson.*
import kotlin.reflect.KClass

private val jsonParser = JsonParser()
private val gson = Gson()

fun String.asJsonObject(): JsonObject = jsonParser.parse(this).asJsonObject

fun <T : Any> JsonObject.getAsList(memberName: String, classOfT: KClass<T>): List<T> {
    if (!this.has(memberName)) {
        throw IllegalArgumentException("Member \"$memberName\" not found in json.")
    }

    val elements = this.get(memberName) as? JsonArray
            ?: throw IllegalArgumentException("Member \"$memberName'\" is not array.")
    val result = mutableListOf<T>()
    elements.forEach { element ->
        result.add(element.toObject(classOfT))
    }

    return result
}

fun <T : Any> JsonElement.toObject(classOfT: KClass<T>): T = gson.fromJson(this, classOfT.java)