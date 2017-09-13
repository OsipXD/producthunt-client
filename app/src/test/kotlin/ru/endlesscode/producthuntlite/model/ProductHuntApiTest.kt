package ru.endlesscode.producthuntlite.model

import org.junit.Test
import kotlin.test.assertNull

class ProductHuntApiTest {

    @Test
    fun getCategories_mustReturnSomething() {
        assertNull(ProductHuntApi.requestCategories())
    }
}