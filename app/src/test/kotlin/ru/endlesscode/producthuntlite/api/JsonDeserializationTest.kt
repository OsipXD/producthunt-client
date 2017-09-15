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

package ru.endlesscode.producthuntlite.api

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.endlesscode.producthuntlite.FileHelper
import java.net.URL
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class JsonDeserializationTest(private val jsonName: String, private val expected: Any) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<out Any>> {
            val category = CategoryData(id = 1, slug = "tech", name = "Tech", color = "#da552f", itemName = "product")
            val post = PostData(
                    id = 1,
                    name = "Awesome Idea #17",
                    day = "2017-07-13",
                    thumbnail = ThumbnailData(1, URL("https://ph-files.imgix.net/81511bc5-e803-4220-bd93-c88dbdcabd50?auto=format&fit=crop&h=570&w=430")),
                    desc = "Great new search engine",
                    categoryId = 2,
                    votesCount = 0,
                    screenshotUrl = ScreenshotUrl(
                            px300 = URL("http://placehold.it/850x850.png"),
                            px850 = URL("http://placehold.it/850x850.png")
                    )
            )


            return listOf(
                    arrayOf("Category", category),
                    arrayOf("Post", post)
            )
        }
    }

    private lateinit var jsonText: String

    @Before
    fun setUp() {
        this.jsonText = FileHelper.readJson(jsonName)
    }

    @Test
    fun deserializationMustBeRight() {
        val actual = jsonText.asJsonObject().toObject(expected::class)
        assertEquals(expected, actual)
    }
}