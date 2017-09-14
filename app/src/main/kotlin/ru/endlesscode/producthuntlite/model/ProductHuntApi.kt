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

package ru.endlesscode.producthuntlite.model

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import ru.endlesscode.producthuntlite.asJsonObject
import ru.endlesscode.producthuntlite.getAsList

object ProductHuntApi {
    private val API_URL = "https://api.producthunt.com/v1"
    private val ACCESS_TOKEN = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff"

    init {
        FuelManager.instance.basePath = API_URL
    }

    fun getCategories(): List<CategoryData> {
        val (_, _, result) = route("/categories").responseString()
        val (data, _) = result

        return data?.asJsonObject()?.getAsList("categories", CategoryData::class) ?: emptyList()
    }

    fun getCategoryFeed(category: String, daysAgo: Int = 0): List<PostData> {
        val validDaysAgo = daysAgo.coerceAtLeast(0)
        val (_, _, result) = route("/categories/$category/posts",
                "days_ago" to validDaysAgo).responseString()
        val (data, _) = result

        return data?.asJsonObject()?.getAsList("posts", PostData::class) ?: emptyList()
    }

    private fun route(value: String, vararg parameters: Pair<String, Any>): Request {
        return value.httpGet(listOf(*parameters)).header(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
                "Authorization" to "Bearer $ACCESS_TOKEN",
                "Host" to "api.producthunt.com"
        )
    }
}