package ru.endlesscode.producthuntlite.model

import org.junit.Test
import kotlin.test.assertEquals

class ProductHuntApiTest {

    @Test
    fun getCategories_mustReturnRightListSize() {
        val categories = ProductHuntApi.getCategories()

        assertEquals(4, categories.size)
    }
}