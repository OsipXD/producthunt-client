package ru.endlesscode.producthuntlite.model

import org.junit.Test
import kotlin.test.assertEquals

class CategoryDataTest {
    private val jsonCategory =
            """{
                "id" : 1,
                "slug" : "tech",
                "name" : "Tech",
                "color" : "#da552f",
                "item_name" : "product"
            }"""

    @Test
    fun fromJson_mustConvertsRight() {
        val category = CategoryData.fromJson(jsonCategory)

        assertEquals(
                expected = "CategoryData(id=1, slug=tech, name=Tech, color=#da552f, itemName=product)",
                actual = category.toString()
        )
    }
}