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

package ru.endlesscode.producthuntlite

import org.junit.Test
import ru.endlesscode.producthuntlite.model.CategoryData
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JsonKtTest {
    companion object {
        private val jsonArrayString = """{
  "categories" : [
    {
      "id" : 1,
      "slug" : "tech",
      "name" : "Tech",
      "color" : "#da552f",
      "item_name" : "product"
    },
    {
      "id" : 2,
      "slug" : "category-2",
      "name" : "Category 2",
      "color" : "#da552f",
      "item_name" : "product"
    }
  ]
}"""

        private val jsonWrongArrayString = """{
  "categories" : "It isn't array"
}"""

        private val jsonObjectString = """{
  "id" : 1,
  "slug" : "tech",
  "name" : "Tech",
  "color" : "#da552f",
  "item_name" : "product"
}"""
    }

    @Test
    fun asJsonObject_mustReturnRightObject() {
        val jsonObject = jsonArrayString.asJsonObject()

        assertTrue(jsonObject.has("categories"))
    }

    @Test
    fun getAsListOf_mustReturnRightList() {
        val actualList = jsonArrayString.asJsonObject().getAsList("categories", CategoryData::class)

        assertEquals(2, actualList.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getAsListOf_mustThrowExceptionWhenPassedWrongMemberName() {
        jsonObjectString.asJsonObject().getAsList("categories", CategoryData::class)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getAsListOf_mustThrowExceptionWhenPassedMemberIsNotArray() {
        jsonWrongArrayString.asJsonObject().getAsList("categories", CategoryData::class)
    }

    @Test
    fun toObject_mustReturnRightObject() {
        val actualObject = jsonObjectString.asJsonObject().toObject(CategoryData::class)
        val expectedObject = CategoryData(1, "tech", "Tech", "#da552f", "product")

        assertEquals(expectedObject, actualObject)
    }
}