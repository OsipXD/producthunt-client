/*
 * This file is part of ProductHuntLite, licensed under the MIT License (MIT).
 *
 * Copyright (c) Osip Fatkullin <osip.fatkullin@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:JvmName("JsonKt")

package ru.endlesscode.producthuntlite.api

import com.google.gson.*
import kotlin.reflect.KClass

fun String.asJsonObject(): JsonObject = JsonParser().parse(this).asJsonObject

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

fun <T : Any> JsonElement.toObject(classOfT: KClass<T>): T = Gson().fromJson(this, classOfT.java)