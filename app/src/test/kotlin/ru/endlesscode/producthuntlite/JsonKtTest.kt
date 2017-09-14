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