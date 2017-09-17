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

package ru.endlesscode.producthuntlite.mvp

import org.junit.Test
import kotlin.test.assertEquals

class HttpExtensionsKtTest {

    @Test
    fun parametrize_mustRightReplaceGivenParameters() {
        val link = "https://ph-files.imgix.net/c398000e-b38f-4432-8dc6-06f487039c64?h=570&w=430&foo=bar"
        val expected = "https://ph-files.imgix.net/c398000e-b38f-4432-8dc6-06f487039c64?h=100&w=100&foo=bar"
        val actual = link.parametrize("h" to 100, "w" to "100")

        assertEquals(expected, actual)
    }

    @Test
    fun parametrize_shouldAddNotExistingParameters() {
        val link = "https://ph-files.imgix.net/c398000e-b38f-4432-8dc6-06f487039c64"
        val expected = "https://ph-files.imgix.net/c398000e-b38f-4432-8dc6-06f487039c64?h=100&w=100"
        val actual1 = link.parametrize("h" to 100, "w" to 100)
        val actual2 = "$link?".parametrize("h" to 100, "w" to 100)

        assertEquals(expected, actual1)
        assertEquals(expected, actual2)
    }
}